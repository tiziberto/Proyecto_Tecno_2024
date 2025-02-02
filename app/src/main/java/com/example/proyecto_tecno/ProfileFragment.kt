package com.example.proyecto_tecno

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Referencias a los EditText y Botón
        val editTextUsuario = view.findViewById<EditText>(R.id.usuario)
        val editTextNombre = view.findViewById<EditText>(R.id.user)
        val editTextContraseña = view.findViewById<EditText>(R.id.contraseña_nueva)
        val btnConfirmarCambios = view.findViewById<Button>(R.id.save_data)

        // Obtener datos del usuario logueado desde SharedPreferences
        val sharedPref: SharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val emailGuardado = sharedPref.getString("email", "correo@gmail.com")
        val nombreGuardado = sharedPref.getString("nombre", "Martin y Tiziano")
        val contraseñaGuardada = sharedPref.getString("contraseña", "")

        // Mostrar datos como hint
        editTextUsuario.hint = emailGuardado
        editTextNombre.hint = nombreGuardado

        // Acción del botón "Confirmar Cambios"
        btnConfirmarCambios.setOnClickListener {
            val nuevoEmail = editTextUsuario.text.toString().trim()
            val nuevoNombre = editTextNombre.text.toString().trim()
            val contraseñaIngresada = editTextContraseña.text.toString().trim()

            // Verificar que la contraseña ingresada sea correcta
            if (contraseñaIngresada == contraseñaGuardada) {
                // Guardar los nuevos datos si no están vacíos
                with(sharedPref.edit()) {
                    if (nuevoEmail.isNotEmpty()) putString("email", nuevoEmail)
                    if (nuevoNombre.isNotEmpty()) putString("nombre", nuevoNombre)
                    apply()
                }
                Toast.makeText(requireContext(), "Datos actualizados correctamente ✅", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Contraseña incorrecta ❌", Toast.LENGTH_SHORT).show()
            }
        }


        return view
    }


}
