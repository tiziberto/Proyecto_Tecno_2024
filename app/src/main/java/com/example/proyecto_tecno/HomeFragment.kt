package com.example.proyecto_tecno

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_tecno.adapter.EventosAdapter
import java.text.Normalizer

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var adapter: EventosAdapter
    private var eventosList: MutableList<Evento> = EventosProvider.EventosList.toMutableList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerEventos)
        val buscador: EditText = view.findViewById(R.id.buscador)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = EventosAdapter(eventosList)
        recyclerView.adapter = adapter

        buscador.addTextChangedListener { userFilter ->
            val filterText = normalizeString(userFilter.toString().trim())
            val eventosFiltered = if (filterText.isEmpty()) {
                eventosList
            } else {
                eventosList.filter { evento ->
                    normalizeString(evento.nombre).contains(filterText, ignoreCase = true) or
                    normalizeString(evento.descripcion).contains(filterText, ignoreCase = true) or
                    normalizeString(evento.ubicacion).contains(filterText, ignoreCase = true)
                }
            }
            adapter.updateEventos(eventosFiltered)
        }

        adapter.onItemClick = {
            val intent = Intent(requireContext(), DetailedActivity::class.java)
            intent.putExtra("evento", it)
            startActivity(intent)
        }
    }

    fun normalizeString(input: String): String {
        val lowerCased = input.lowercase()
        return Normalizer.normalize(lowerCased, Normalizer.Form.NFD)
            .replace(Regex("\\p{M}"), "")
    }
}

