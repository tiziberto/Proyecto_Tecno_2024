package com.example.proyecto_tecno.ui.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_tecno.databinding.ActivityMainBinding
import com.example.proyecto_tecno.databinding.ViewEventoItemBinding

class EventosAdapter(private val eventos : List<Evento>) : RecyclerView.Adapter<EventosAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewEventoItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return eventos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(eventos[position])
    }

    class ViewHolder(private val binding: ViewEventoItemBinding):
        RecyclerView.ViewHolder(binding.root){
       fun bind(evento: Evento){
            binding.TituloEvento.text = evento.nombre
           binding.DescripcionEvento.text = evento.descripcion
       }
    }
}