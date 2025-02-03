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
import androidx.lifecycle.lifecycleScope
import com.example.proyecto_tecno.databse.FindItDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // referencias a los elements
        val editTextUsuario = view.findViewById<EditText>(R.id.usuario)
        val editTextNombre = view.findViewById<EditText>(R.id.user)
        val editTextContraseña = view.findViewById<EditText>(R.id.contraseña_nueva)
        val btnConfirmarCambios = view.findViewById<Button>(R.id.save_data)

        // obtener datos del usuario logueado (SharedPreferences)
        val sharedPref: SharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val emailGuardado = sharedPref.getString("email", "correo@gmail.com") ?: "correo@gmail.com"
        val nombreGuardado = sharedPref.getString("nombre", "Martin y Tiziano")
        val contraseñaGuardada = sharedPref.getString("contraseña", "")

        // mostrar datos como hint
        editTextUsuario.hint = emailGuardado
        editTextNombre.hint = nombreGuardado

        // acción del botón "Confirmar Cambios"
        btnConfirmarCambios.setOnClickListener {
            val nuevoEmail = editTextUsuario.text.toString().trim()
            val nuevoNombre = editTextNombre.text.toString().trim()
            val contraseñaIngresada = editTextContraseña.text.toString().trim()

            // verificar contraseña
            if (contraseñaIngresada == contraseñaGuardada) {
                if (nuevoEmail.isEmpty() || nuevoNombre.isEmpty()) {
                    Toast.makeText(requireContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                } else {
                    // actualizar SharedPreferences
                    with(sharedPref.edit()) {
                        if (nuevoEmail.isNotEmpty()) putString("email", nuevoEmail)
                        if (nuevoNombre.isNotEmpty()) putString("nombre", nuevoNombre)
                        apply()
                    }

                    // actualizar bd
                    lifecycleScope.launch(Dispatchers.IO) {
                        val db = FindItDataBase.getDatabase(requireContext())
                        val userDao = db.UsuarioDao()
                        val usuario = userDao.getUsuarioByEmail(emailGuardado) // Obtener usuario por email
                        if (usuario != null) {
                            if (nuevoEmail.isNotEmpty()) usuario.mail = nuevoEmail
                            if (nuevoNombre.isNotEmpty()) usuario.nombre = nuevoNombre
                            userDao.updateUser(usuario)
                            withContext(Dispatchers.Main) {
                                Toast.makeText(requireContext(), "Cambio de datos realizado con éxito", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            //no se encuentra el usuario
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Contraseña incorrecta ❌", Toast.LENGTH_SHORT).show()
            }
        }



        return view
    }


}
