package com.example.proyecto_tecno

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.proyecto_tecno.AlarmNotification.Companion.NOTIFICATION_ID
import com.example.proyecto_tecno.databse.EventoEntity
import com.example.proyecto_tecno.databse.FinditApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DetailedActivity : AppCompatActivity() {
    private lateinit var myButton: ImageButton
    private lateinit var shareButton: ImageButton
    private var isIconClicked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)
        enableEdgeToEdge()

        val evento = intent.getParcelableExtra<EventoEntity>("evento")

        if (evento != null) {
            val nombre: TextView = findViewById(R.id.detailed_nombre)
            val descripcion: TextView = findViewById(R.id.detailed_descripcion)
            val ubicacion: TextView = findViewById(R.id.detailed_ubicacion)
            val imagen: ImageView = findViewById(R.id.detailed_imagen)
            val fecha: TextView = findViewById(R.id.fecha_dato)

            nombre.text = evento.nombre
            descripcion.text = evento.descripcion
            ubicacion.text = evento.ubicacion
            fecha.text = formatDate(evento.fecha)

            Glide.with(this)
                .load(evento.foto)
                .into(imagen)

            // verificar evento en base de datos al iniciar la actividad
            verificarEventoEnBaseDeDatos(evento)

            shareButton = findViewById(R.id.btnCompartir)

            shareButton.setOnClickListener {
                compartirEvento(evento)
            }

        } else {
            finish()
        }

        myButton = findViewById(R.id.Star)

        myButton.setOnClickListener {
            if (isIconClicked) {
                myButton.setImageResource(R.drawable.baseline_star_border_24)
                showMsg(getString(R.string.delete_favorites))
                if (evento != null) {
                    eliminarDeFavoritos(evento)
                }
            } else {
                showMsg(getString(R.string.add_favorites))
                if (evento != null) {
                    addToFavourites(evento)
                }
                myButton.setImageResource(R.drawable.baseline_star_24)
            }
            isIconClicked = !isIconClicked
        }
    }

    // formatear la fecha
    private fun formatDate(fecha: String): String {
        return try {
            // la fecha está en formato ISO 8601 (2025-02-10T10:00:00-03:00)
            val formatEntrada = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
            val parsedDate = formatEntrada.parse(fecha)

            // formatear la fecha en el formato "DD/MM/YYYY HH:mm"
            val formatSalida = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            parsedDate?.let {
                formatSalida.format(it)
            } ?: ""
        } catch (e: Exception) {
            ""
        }
    }


    private fun eliminarDeFavoritos(evento: EventoEntity) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                FinditApplication.dataBase.EventoDao().deleteEvento(evento)
                withContext(Dispatchers.Main) {
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showMsg("Error al eliminar el evento: ${e.message}")
                }
            }
        }
    }

    private fun showMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun addToFavourites(evento: EventoEntity) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val result = FinditApplication.dataBase.EventoDao().addEvento(evento)
                if (result > 0) {
                    withContext(Dispatchers.Main) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                            if (!alarmManager.canScheduleExactAlarms()) {
                                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                                startActivity(intent)
                            } else {
                                scheduleNotification(applicationContext)
                            }
                        } else {
                            scheduleNotification(applicationContext)
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showMsg("Error: ${e.message}")
                }
            }
        }
    }

    private lateinit var sharedPreferences: SharedPreferences

    private fun scheduleNotification(context: Context) {
        sharedPreferences = context.applicationContext.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val anticipacion = sharedPreferences.getInt("anticipacion", 30)
        val notificacionesActivadas = sharedPreferences.getBoolean("notificaciones", true)

        cancelarTodasLasAlarmas()
        val intent = Intent(applicationContext,AlarmNotification::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        lifecycleScope.launch(Dispatchers.IO) {
            // obtener los eventos desde la base de datos
            val eventos = FinditApplication.dataBase.EventoDao().getAllEventos()
            // recorrer los eventos
            for (evento in eventos) {
                val fechaEvento = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault()).parse(evento.fecha)
                val tiempoEnMillis = fechaEvento?.time ?: continue
                val tiempoAlarma = tiempoEnMillis - anticipacion * 60 * 1000

                // Programar la alarma
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, tiempoAlarma, pendingIntent)
            }
        }
    }

    private val pendingIntents: MutableList<PendingIntent> = mutableListOf()

    private fun cancelarTodasLasAlarmas() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        for (pendingIntent in pendingIntents) {
            alarmManager.cancel(pendingIntent)
        }
    }


    private fun compartirEvento(evento: EventoEntity) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_SUBJECT, "Evento en FindIt")
            putExtra(Intent.EXTRA_TEXT, "¡Mira este evento! \n\n${evento.nombre}\n${evento.descripcion}\n" +
                    "Fecha: ${evento.fecha}")
            type = "text/plain"
            val imageUri = Uri.parse(evento.foto)
            putExtra(Intent.EXTRA_STREAM, imageUri)
            type = "image/*"
        }

        // abrir el "chooser"
        val shareIntent = Intent.createChooser(intent, "Compartir evento con...")
        startActivity(shareIntent)
    }

    private fun verificarEventoEnBaseDeDatos(evento: EventoEntity) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // buscar el evento en la base de datos por su ID
                val eventoEnDB = FinditApplication.dataBase.EventoDao().getEventoById(evento.id)
                withContext(Dispatchers.Main) {
                    if (eventoEnDB != null) {
                        // si el evento está en la base de datos
                        myButton.setImageResource(R.drawable.baseline_star_24)
                        isIconClicked = true // Actualizar el estado del botón
                    } else {
                        // si el evento no está en la base de dato
                        myButton.setImageResource(R.drawable.baseline_star_border_24)
                        isIconClicked = false // Actualizar el estado del botón
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


