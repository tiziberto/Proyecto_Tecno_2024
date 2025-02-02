package com.example.proyecto_tecno

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.proyecto_tecno.database.UsuarioRepository
import com.example.proyecto_tecno.databse.FindItDataBase
import com.example.proyecto_tecno.ui.PantallaRegistroActivity
import com.example.proyecto_tecno.viewmodels.UsuarioViewModel
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var password: EditText
    private lateinit var togglePasswordVisibility: Button
    private lateinit var usuarioViewModel: UsuarioViewModel
    private lateinit var editTextUsuario: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var btnIniciarSesion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = FindItDataBase.getDatabase(this)
        val repository = UsuarioRepository(database.UsuarioDao())
        usuarioViewModel = UsuarioViewModel(repository)

        editTextUsuario = findViewById(R.id.usuario)
        editTextPassword = findViewById(R.id.password)
        btnIniciarSesion = findViewById(R.id.cambiar_password)

        btnIniciarSesion.setOnClickListener {
            val email = editTextUsuario.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                iniciarSesion(email, password)
            } else {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        val btn_register : Button = findViewById(R.id.pantallaregistro)
        btn_register.setOnClickListener{
            val intent: Intent = Intent(this, PantallaRegistroActivity::class.java)
            startActivity(intent)
        }

        val recuperarpassword : Button = findViewById(R.id.recuperarpassword)
        recuperarpassword.setOnClickListener{
            val intent: Intent = Intent(this, PantallaRecuperarPasswordActivity::class.java)
            startActivity(intent)
        }

        password = findViewById(R.id.password)
        togglePasswordVisibility = findViewById(R.id.togglePasswordVisibility)

        togglePasswordVisibility.setOnClickListener {
            if (password.transformationMethod is PasswordTransformationMethod) {
                password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                togglePasswordVisibility.text = "🙈"
            } else {
                password.transformationMethod = PasswordTransformationMethod.getInstance()
                togglePasswordVisibility.text = "🙉️"
            }
            password.setSelection(password.text.length)
        }


    }




    private fun iniciarSesion(email: String, password: String) {
        lifecycleScope.launch {
            val usuario = usuarioViewModel.getUsuarioByEmailAndPassword(email, password)
            if (usuario != null) {
                Toast.makeText(this@LoginActivity, getString(R.string.succes_login), Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity, PantallaHomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@LoginActivity, getString(R.string.failed_login), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
