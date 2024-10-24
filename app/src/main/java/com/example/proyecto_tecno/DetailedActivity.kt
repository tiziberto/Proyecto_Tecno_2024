package com.example.proyecto_tecno

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class DetailedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)
        enableEdgeToEdge()
        val evento = intent.getParcelableExtra<Evento>("evento")
        if (evento != null){
            val nombre : TextView = findViewById(R.id.detailed_nombre)
            val descripcion : TextView = findViewById(R.id.detailed_descripcion)
            val ubicacion : TextView = findViewById(R.id.detailed_ubicacion)
            val imagen : ImageView = findViewById(R.id.detailed_imagen)

            nombre.text = evento.nombre
            descripcion.text = evento.descripcion
            ubicacion.text = evento.ubicacion
            Glide.with(this)
                .load(evento.foto) // Aquí asumo que evento.foto es la URL
                .into(imagen)
        }
    }
}
