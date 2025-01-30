package com.example.proyecto_tecno.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import androidx.lifecycle.lifecycleScope
import com.example.proyecto_tecno.R
import com.example.proyecto_tecno.databse.FinditApplication
import com.example.proyecto_tecno.models.UsuarioEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PantallaRegistroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_registro)

        val etNombre: EditText = findViewById(R.id.nombre)
        val etMail: EditText = findViewById(R.id.codigo)
        val etContraseña: EditText = findViewById(R.id.contraseña_nueva)
        val etRepetirContraseña: EditText = findViewById(R.id.repetircontraseñanueva)
        val btnRegistrarse: Button = findViewById(R.id.registrarse)
        val btnLogin: Button = findViewById(R.id.pantallalogin)


        btnRegistrarse.setOnClickListener {
            val nombre = etNombre.text.toString()
            val mail = etMail.text.toString()
            val contraseña = etContraseña.text.toString()
            val repetirContraseña = etRepetirContraseña.text.toString()

            if (nombre.isEmpty() || mail.isEmpty() || contraseña.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            } else if (contraseña != repetirContraseña) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            } else {
                val usuario = UsuarioEntity(nombre = nombre, mail = mail, clave  = contraseña)

                // Usar Room para registrar el usuario
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val result = FinditApplication.dataBase.UsuarioDao().insertUsuario(usuario)
                        withContext(Dispatchers.Main) {
                            if (result > 0) {
                                Toast.makeText(this@PantallaRegistroActivity, "Registro exitoso", Toast.LENGTH_SHORT).show()
                                finish()  // Terminar la actividad después del registro
                            } else {
                                Toast.makeText(this@PantallaRegistroActivity, "Error al registrar", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@PantallaRegistroActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        btnLogin.setOnClickListener {
            finish()  // Volver a la pantalla de login
        }
    }
}
