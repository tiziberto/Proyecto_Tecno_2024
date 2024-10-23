package com.example.proyecto_tecno.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_tecno.Evento
import com.example.proyecto_tecno.R

class EventosAdapter(var EventosList:List<Evento>): RecyclerView.Adapter<EventosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventosViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EventosViewHolder(layoutInflater.inflate(R.layout.item_evento, parent, false))
    }

    override fun getItemCount(): Int = EventosList.size

    override fun onBindViewHolder(holder: EventosViewHolder, position: Int) {
        val item = EventosList[position]
        holder.render(item)
    }
}