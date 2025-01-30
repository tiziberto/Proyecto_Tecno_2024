package com.example.proyecto_tecno

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Context

class LoginActivity : AppCompatActivity() {
    private val predefinedEmail = "correo@gmail.com"
    private val predefinedPassword = "1234"
    private lateinit var password: EditText
    private lateinit var togglePasswordVisibility: Button



    fun clearDatabase(context: Context) {
        val dbName = ".FindItDatabase"
        context.deleteDatabase(dbName)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        clearDatabase(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
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

        val usuarioInput: EditText = findViewById(R.id.usuario)
        val contraseñaInput: EditText = findViewById(R.id.password)

        val btn_login : Button = findViewById(R.id.cambiar_password)
        btn_login.setOnClickListener{
            val emailInput = usuarioInput.text.toString()
            val passwordInput = contraseñaInput.text.toString()

            if (emailInput == predefinedEmail && passwordInput == predefinedPassword) {
                val intent: Intent = Intent(this, PantallaHomeActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
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
    }
}