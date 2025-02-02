package com.example.proyecto_tecno

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.proyecto_tecno.databse.EventoEntity
import com.example.proyecto_tecno.databse.FinditApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

class DetailedActivity : AppCompatActivity() {
    private lateinit var myButton: ImageButton
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

            // Verificar si el evento está en la base de datos al iniciar la actividad
            verificarEventoEnBaseDeDatos(evento)
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

    // Método para formatear la fecha
    private fun formatDate(fecha: String): String {
        return try {
            // Suponiendo que la fecha está en formato ISO 8601 (como "2025-02-10T10:00:00-03:00")
            val formatEntrada = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
            val parsedDate = formatEntrada.parse(fecha)

            // Formatear la fecha en el formato "DD/MM/YYYY HH:mm"
            val formatSalida = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            parsedDate?.let {
                formatSalida.format(it)
            } ?: ""
        } catch (e: Exception) {
            // Si hay algún error al parsear la fecha, devolver una cadena vacía o manejar el error
            ""
        }
    }


    private fun eliminarDeFavoritos(evento: EventoEntity) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                FinditApplication.dataBase.EventoDao().deleteEvento(evento)
                withContext(Dispatchers.Main) {
                    //showMsg("Evento eliminado de favoritos")
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
                        //showMsg("Evento añadido a favoritos con ID: $result")
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        //showMsg("Error al añadir el evento a favoritos")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showMsg("Error: ${e.message}")
                }
            }
        }
    }

    private fun verificarEventoEnBaseDeDatos(evento: EventoEntity) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Buscar el evento en la base de datos por su ID
                val eventoEnDB = FinditApplication.dataBase.EventoDao().getEventoById(evento.id)

                // Actualizar la UI en el hilo principal
                withContext(Dispatchers.Main) {
                    if (eventoEnDB != null) {
                        // Si el evento está en la base de datos, cambiar la imagen del botón
                        myButton.setImageResource(R.drawable.baseline_star_24)
                        isIconClicked = true // Actualizar el estado del botón
                    } else {
                        // Si el evento no está en la base de datos, usar la imagen de estrella vacía
                        myButton.setImageResource(R.drawable.baseline_star_border_24)
                        isIconClicked = false // Actualizar el estado del botón
                    }
                }
            } catch (e: Exception) {
                // Manejar errores
                e.printStackTrace()
            }
        }
    }
}


