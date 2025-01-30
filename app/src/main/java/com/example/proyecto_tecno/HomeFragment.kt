package com.example.proyecto_tecno

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_tecno.adapter.EventosAdapter
import com.example.proyecto_tecno.databse.EventoEntity
import com.example.proyecto_tecno.databse.EventoService
import com.example.proyecto_tecno.databse.FinditApplication

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.Normalizer

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var adapter: EventosAdapter
    private lateinit var service: EventoService
    private var eventosList: MutableList<EventoEntity> = mutableListOf() // Lista completa de eventos
    private var isFiltroActivo: Boolean = false // Estado del filtro
    private var eventosFiltrados: MutableList<EventoEntity> = mutableListOf() // Lista filtrada de eventos
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Inicializa Retrofit y el servicio de API
        setupRetrofit()

        // Configura el RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerEventos)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = EventosAdapter(eventosList) // Inicializa el adaptador con la lista vacía
        recyclerView.adapter = adapter

        // Configura el buscador
        val buscador: EditText = view.findViewById(R.id.buscador)
        buscador.addTextChangedListener { userFilter ->
            val filterText = normalizeString(userFilter.toString().trim())
            val eventosFiltered = if (filterText.isEmpty()) {
                eventosList // Si el buscador está vacío, muestra todos los eventos
            } else {
                // Filtra los eventos por nombre, descripción o ubicación
                eventosList.filter { evento ->
                    normalizeString(evento.nombre).contains(filterText, ignoreCase = true) ||
                            normalizeString(evento.descripcion).contains(filterText, ignoreCase = true) ||
                            normalizeString(evento.ubicacion).contains(filterText, ignoreCase = true)
                }
            }
            adapter.updateEventos(eventosFiltered) // Actualiza la lista del adaptador

        }

        // Configura el evento de clic en el adaptador
        adapter.onItemClick = { evento ->
            val intent = Intent(requireContext(), DetailedActivity::class.java)
            intent.putExtra("evento", evento) // Pasa el evento seleccionado a la actividad de detalle
            startActivity(intent)
        }

        // Configura el botón de filtrar favoritos
        val btnFiltrarFavoritos: Button = view.findViewById(R.id.btnFiltrarFavoritos)
        btnFiltrarFavoritos.setOnClickListener {
            isFiltroActivo = !isFiltroActivo // Alternar el estado del filtro
            if (isFiltroActivo) {
                filtrarFavoritos() // Mostrar solo los eventos en la base de datos
                btnFiltrarFavoritos.text = "Mostrar todos" // Cambiar el texto del botón
            } else {
                mostrarListaCompleta() // Mostrar todos los eventos
                btnFiltrarFavoritos.text = "Mostrar favoritos" // Cambiar el texto del botón
            }
        }

        // Carga los eventos desde la API
        getEventos()
    }

    private fun mostrarListaCompleta() {
        adapter.updateEventos(eventosList) // Actualiza el RecyclerView con la lista completa
    }

    // Filtra los eventos para mostrar solo los que están en la base de datos
    private fun filtrarFavoritos() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Obtener los IDs de los eventos en la base de datos
                val idsFavoritos = FinditApplication.dataBase.EventoDao().getAllIds()

                // Filtrar la lista de eventos para incluir solo los que están en la base de datos
                eventosFiltrados.clear()
                eventosFiltrados.addAll(eventosList.filter { it.id in idsFavoritos })

                // Actualizar el RecyclerView en el hilo principal
                withContext(Dispatchers.Main) {
                    adapter.updateEventos(eventosFiltrados)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showMsg("Error al filtrar eventos: ${e.message}")
                }
            }
        }
    }

    // Configura Retrofit y el servicio de API
    private fun setupRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(EventoService::class.java)
    }

    // Obtiene los eventos desde la API y actualiza la lista
    private fun getEventos() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val eventos = service.getEventos() // Llama al servicio para obtener los eventos
                Log.d("API_RESPONSE", "Eventos obtenidos: $eventos") // Inspecciona la respuesta

                lifecycleScope.launch(Dispatchers.Main) {
                    if (eventos.isNotEmpty()) {
                        eventosList.clear()
                        eventosList.addAll(eventos) // Agrega los eventos a la lista
                        adapter.updateEventos(eventosList) // Actualiza el adaptador
                    } else {
                        Log.d("API_RESPONSE", "La lista de eventos está vacía")
                    }
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error al obtener eventos: ${e.message}")
            }
        }
    }

    // Normaliza una cadena eliminando acentos y convirtiéndola a minúsculas
    private fun normalizeString(input: String): String {
        val lowerCased = input.lowercase()
        return Normalizer.normalize(lowerCased, Normalizer.Form.NFD)
            .replace(Regex("\\p{M}"), "")
    }

    // Muestra un mensaje en la UI
    private fun showMsg(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}