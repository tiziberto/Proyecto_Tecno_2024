package com.example.proyecto_tecno.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.proyecto_tecno.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.slider.Slider
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sliderAnticipacion: Slider
    private lateinit var anticipacionValor: TextView
    private lateinit var switchNotificaciones: SwitchMaterial
    private lateinit var btnConfirmarCambios: MaterialButton
    private lateinit var btnEnviarCorreo: MaterialButton
    private lateinit var btnChangeLanguage: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        sliderAnticipacion = view.findViewById(R.id.sliderAnticipacion)
        anticipacionValor = view.findViewById(R.id.anticipacionValor)
        switchNotificaciones = view.findViewById(R.id.switchNotificaciones)
        btnConfirmarCambios = view.findViewById(R.id.btnConfirmarCambios)
        btnEnviarCorreo = view.findViewById(R.id.btnEnviarCorreo)
        btnChangeLanguage = view.findViewById(R.id.btnChangeLanguage)

        sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)

        cargarPreferencias()

        sliderAnticipacion.addOnChangeListener { _, value, _ ->
            anticipacionValor.text = "${value.toInt()} " + getString(R.string.minutes)
        }

        btnConfirmarCambios.setOnClickListener {
            guardarPreferencias()
        }

        btnEnviarCorreo.setOnClickListener {
            enviarCorreoDesarrollador()
        }

        btnChangeLanguage.setOnClickListener {
            cambiarIdioma()
        }

        return view
    }

    private fun cargarPreferencias() {
        val anticipacion = sharedPreferences.getInt("anticipacion", 30)
        val notificacionesActivadas = sharedPreferences.getBoolean("notificaciones", true)

        sliderAnticipacion.value = anticipacion.toFloat()
        anticipacionValor.text = "$anticipacion minutos"
        switchNotificaciones.isChecked = notificacionesActivadas
    }

    private fun guardarPreferencias() {
        val editor = sharedPreferences.edit()
        editor.putInt("anticipacion", sliderAnticipacion.value.toInt())
        editor.putBoolean("notificaciones", switchNotificaciones.isChecked)
        editor.apply()
    }

    private fun enviarCorreoDesarrollador() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            putExtra(Intent.EXTRA_SUBJECT, "Consulta sobre la aplicación") // asunto
            putExtra(Intent.EXTRA_TEXT, "Escribe tu mensaje aquí...") // msg
            data = Uri.parse("mailto:developer@example.com") // mail del desarrollador
        }
        startActivity(Intent.createChooser(intent, "Enviar correo"))
    }

    private fun cambiarIdioma() {
        val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        startActivity(intent)
    }

}