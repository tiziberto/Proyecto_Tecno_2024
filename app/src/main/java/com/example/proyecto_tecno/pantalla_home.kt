package com.example.proyecto_tecno

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_tecno.adapter.EventosAdapter
import com.example.proyecto_tecno.adapter.EventosViewHolder
import com.example.proyecto_tecno.databinding.ActivityPantallaHomeBinding

class pantalla_home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_home)
        initRecyclerView()
    }

    fun initRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerEventos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = EventosAdapter(EventosProvider.EventosList)
    }
}