package com.example.proyecto_tecno

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.proyecto_tecno.databse.FindItDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SolicitudCodigoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_solicitud_codigo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val emailTextView: TextView = findViewById(R.id.email_text)
        val sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("user_email", "No registrado")
        emailTextView.text = email

        val codeInput: EditText = findViewById(R.id.codigo)
        val passwordInput: EditText = findViewById(R.id.contraseña_nueva)
        val repeatPasswordInput: EditText = findViewById(R.id.repetircontraseñanueva)
        val btnChangePassword: Button = findViewById(R.id.cambiar_password)
        btnChangePassword.isEnabled = false

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isCodeValid = codeInput.text.toString() == "1234"
                val arePasswordsMatching = passwordInput.text.toString() == repeatPasswordInput.text.toString()
                val arePasswordsNotEmpty = passwordInput.text.isNotEmpty() && repeatPasswordInput.text.isNotEmpty()
                btnChangePassword.isEnabled = isCodeValid && arePasswordsMatching && arePasswordsNotEmpty
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        codeInput.addTextChangedListener(textWatcher)
        passwordInput.addTextChangedListener(textWatcher)
        repeatPasswordInput.addTextChangedListener(textWatcher)

        btnChangePassword.setOnClickListener {
            val newPassword = passwordInput.text.toString()
            if (email != null && email != "No registrado") {
                lifecycleScope.launch(Dispatchers.IO) {
                    val db = FindItDataBase.getDatabase(applicationContext)
                    val userDao = db.UsuarioDao()
                    val user = userDao.getUsuarioByEmail(email)
                    if (user != null) {
                        user.clave = newPassword
                        userDao.updateUser(user)
                        runOnUiThread {
                            Toast.makeText(applicationContext, "Contraseña actualizada con éxito", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@SolicitudCodigoActivity, LoginActivity::class.java)
                            startActivity(intent)
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(applicationContext, "Error: usuario no encontrado", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}
