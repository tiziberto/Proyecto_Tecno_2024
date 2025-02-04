package com.example.proyecto_tecno

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
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

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(this, "Permiso de notificaciones concedido ✅", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permiso de notificaciones denegado ❌", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()
        createChannel() // para las notis
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

        val recuperarPassword: Button = findViewById(R.id.recuperarpassword)
        recuperarPassword.setOnClickListener {
            val intent = Intent(this, PantallaRecuperarPasswordActivity::class.java)
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

    companion object {
        const val MY_CHANNEL_ID = "myChannel"
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                MY_CHANNEL_ID,
                "My Super Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Canal para Find It"
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun iniciarSesion(email: String, password: String) {
        lifecycleScope.launch {
            val usuario = usuarioViewModel.getUsuarioByEmailAndPassword(email, password)
            if (usuario != null) {
                val sharedPref = getSharedPreferences("UserData", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("email", usuario.mail)
                    putString("nombre", usuario.nombre)
                    putString("contraseña", usuario.clave)
                    apply()
                }
                Toast.makeText(this@LoginActivity, "Inicio de sesión exitoso ✅", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity, PantallaHomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@LoginActivity, "Error al iniciar sesión ❌", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
