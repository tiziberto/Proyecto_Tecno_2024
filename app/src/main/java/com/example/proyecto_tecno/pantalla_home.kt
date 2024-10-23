package com.example.proyecto_tecno

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_tecno.adapter.EventosAdapter
import com.example.proyecto_tecno.databinding.ActivityPantallaHomeBinding

private lateinit var binding: ActivityPantallaHomeBinding
private var eventosList: MutableList<Evento> =
    EventosProvider.EventosList.toMutableList()
private lateinit var adapter: EventosAdapter

class pantalla_home : AppCompatActivity() {
        private lateinit var binding: ActivityPantallaHomeBinding
        private var eventosList: MutableList<Evento> = EventosProvider.EventosList.toMutableList()
        private lateinit var adapter: EventosAdapter

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityPantallaHomeBinding.inflate(layoutInflater)
            setContentView(binding.root)

            initRecyclerView()
            setupSearch() // Método para configurar el buscador
        }

        private fun initRecyclerView() {
            val recyclerView = binding.recyclerEventos
            recyclerView.layoutManager = LinearLayoutManager(this)
            adapter = EventosAdapter(eventosList)
            recyclerView.adapter = adapter
        }

        private fun setupSearch() {
            binding.buscador.addTextChangedListener { userFilter ->
                val filterText = userFilter.toString().trim() // Obtener texto filtrado y eliminar espacios
                val eventosFiltered = if (filterText.isEmpty()) {
                    eventosList // Si el filtro está vacío, muestra toda la lista
                } else {
                    eventosList.filter { evento ->
                        evento.nombre.contains(filterText, ignoreCase = true) // Filtrado sin distinción de mayúsculas
                    }
                }
                adapter.updateEventos(eventosFiltered)
            }
        }

        override fun onBackPressed() {
            // No hacer nada
        }
    }
