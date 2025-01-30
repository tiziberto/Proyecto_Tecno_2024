package com.example.proyecto_tecno.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_tecno.databse.EventoEntity
import com.example.proyecto_tecno.R

class EventosAdapter(
    private var EventosList:List<EventoEntity>

): RecyclerView.Adapter<EventosViewHolder>() {

    var onItemClick : ((EventoEntity)-> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventosViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EventosViewHolder(layoutInflater.inflate(R.layout.item_evento, parent, false))
    }

    override fun getItemCount(): Int = EventosList.size

    override fun onBindViewHolder(holder: EventosViewHolder, position: Int) {
        val item = EventosList[position]
        holder.render(item)

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(item)
        }
    }

    fun updateEventos(nuevaEventosList: List<EventoEntity>) {
        this.EventosList = nuevaEventosList
        notifyDataSetChanged()
    }

}