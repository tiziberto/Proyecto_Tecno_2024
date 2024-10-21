package com.example.proyecto_tecno

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btn_login : Button = findViewById(R.id.cambiar_password)
        btn_login.setOnClickListener{
            //val intent: Intent = Intent(this, pantalla::class.java)
            startActivity(intent)
        }

        val btn_register : Button = findViewById(R.id.pantallaregistro)
        btn_register.setOnClickListener{
            val intent: Intent = Intent(this, pantalla_registro::class.java)
            startActivity(intent)
        }

        val recuperarpassword : Button = findViewById(R.id.recuperarpassword)
        recuperarpassword.setOnClickListener{
            val intent: Intent = Intent(this, pantalla_recuperar_password::class.java)
            startActivity(intent)
        }
    }
}