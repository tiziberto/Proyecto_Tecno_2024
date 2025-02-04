package com.example.proyecto_tecno

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
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
        // inicia Retrofit y el servicio de API
        setupRetrofit()

        // configura el RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerEventos)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = EventosAdapter(eventosList) // inicializa el adapter con la lista vacia
        recyclerView.adapter = adapter

        val buscador: EditText = view.findViewById(R.id.buscador)
        buscador.addTextChangedListener { userFilter ->
            val filterText = normalizeString(userFilter.toString().trim())
            val eventosFiltered = if (filterText.isEmpty()) {
                eventosList
            } else {
                eventosList.filter { evento ->
                    normalizeString(evento.nombre).contains(filterText, ignoreCase = true) ||
                    normalizeString(evento.descripcion).contains(filterText, ignoreCase = true) ||
                    normalizeString(evento.ubicacion).contains(filterText, ignoreCase = true)
                }
            }
            adapter.updateEventos(eventosFiltered)

        }

        // clic en el adapter
        adapter.onItemClick = { evento ->
            val intent = Intent(requireContext(), DetailedActivity::class.java)
            intent.putExtra("evento", evento)
            startActivity(intent)
        }

        // filtrar favoritos
        val btnFiltrarFavoritos: Button = view.findViewById(R.id.btnFiltrarFavoritos)
        btnFiltrarFavoritos.setOnClickListener {
            isFiltroActivo = !isFiltroActivo // alternar el filtro
            if (isFiltroActivo) {
                filtrarFavoritos()
                btnFiltrarFavoritos.text = getString(R.string.show_all)
            } else {
                mostrarListaCompleta()
                btnFiltrarFavoritos.text = getString(R.string.show_favorites)
            }
        }

        // carga de los eventos desde la API
        getEventos()
    }

    private fun mostrarListaCompleta() {
        adapter.updateEventos(eventosList) // actualiza el RecyclerView con la lista completa
    }

    // mostrar solo los eventos en bd
    private fun filtrarFavoritos() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // obtener los IDs de bd
                val idsFavoritos = FinditApplication.dataBase.EventoDao().getAllIds()

                // filtrar
                eventosFiltrados.clear()
                eventosFiltrados.addAll(eventosList.filter { it.id in idsFavoritos })

                // actualizar el RecyclerView
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

    private fun setupRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(EventoService::class.java)
    }

    // obtiene los eventos desde la API y actualiza la lista
    private fun getEventos() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val eventos = service.getEventos() // s
                Log.d("API_RESPONSE", "Eventos obtenidos: $eventos") //

                lifecycleScope.launch(Dispatchers.Main) {
                    if (eventos.isNotEmpty()) {
                        eventosList.clear()
                        eventosList.addAll(eventos)
                        adapter.updateEventos(eventosList)
                    } else {
                        Log.d("API_RESPONSE", "La lista de eventos está vacía")
                    }
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error al obtener eventos: ${e.message}")
            }
        }
    }

    // normalizar cadenas de busqueda
    private fun normalizeString(input: String): String {
        val lowerCased = input.lowercase()
        return Normalizer.normalize(lowerCased, Normalizer.Form.NFD)
            .replace(Regex("\\p{M}"), "")
    }

    private fun showMsg(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}