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
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailedActivity : AppCompatActivity() {
    private lateinit var myButton: ImageButton
    private var isIconClicked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)
        enableEdgeToEdge()

        val evento = intent.getParcelableExtra<Evento>("evento")

        if (evento != null) {
            val nombre: TextView = findViewById(    R.id.detailed_nombre)
            val descripcion: TextView = findViewById(R.id.detailed_descripcion)
            val ubicacion: TextView = findViewById(R.id.detailed_ubicacion)
            val imagen: ImageView = findViewById(R.id.detailed_imagen)

            nombre.text = evento.nombre
            descripcion.text = evento.descripcion
            ubicacion.text = evento.ubicacion

            Glide.with(this)
                .load(evento.foto)
                .into(imagen)
        } else {
            finish()
        }

        myButton = findViewById(R.id.Star)

        myButton.setOnClickListener {
            if (isIconClicked) {
                myButton.setImageResource(R.drawable.baseline_star_border_24)
                showMsg("Eliminado de favoritos")
            } else {
                showMsg("Añadido a favoritos")
                if (evento != null) {
                    addToFavourites(evento)
                }
                myButton.setImageResource(R.drawable.baseline_star_24)
            }
            isIconClicked = !isIconClicked
        }
    }
    private fun showMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun addToFavourites(evento: Evento) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val result = FinditApplication.dataBase.EventoDao().addEvento(evento)
                if (result > 0) {
                    withContext(Dispatchers.Main) {
                        showMsg("Evento añadido a favoritos con ID: $result")
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        showMsg("Error al añadir el evento a favoritos")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showMsg("Error: ${e.message}")
                }
            }
        }
    }

}
