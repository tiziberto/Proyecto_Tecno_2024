package com.example.proyecto_tecno

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener

class PantallaRecuperarPasswordActivity : AppCompatActivity() {
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pantalla_recuperar_password)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        database = openOrCreateDatabase("FindItDatabase", Context.MODE_PRIVATE, null)

        val emailInput: EditText = findViewById(R.id.codigo)
        val btnLogin: Button = findViewById(R.id.pantallalogin)
        val btnRequestCode: Button = findViewById(R.id.cambiar_password)
        btnRequestCode.isEnabled = false  // Deshabilitar por defecto

        emailInput.addTextChangedListener {
            val email = it.toString()
            btnRequestCode.isEnabled = emailExistsInDatabase(email)
        }

        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btnRequestCode.setOnClickListener {
            val email = emailInput.text.toString()
            if (emailExistsInDatabase(email)) {
                saveEmailToSharedPreferences(email)
                val intent = Intent(this, SolicitudCodigoActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Correo no encontrado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun emailExistsInDatabase(email: String): Boolean {
        val cursor = database.rawQuery("SELECT * FROM usuario WHERE mail = ?", arrayOf(email))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    private fun saveEmailToSharedPreferences(email: String) {
        val sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("user_email", email)
            apply()
        }
    }
}
