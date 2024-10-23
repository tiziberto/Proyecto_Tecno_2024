package com.example.proyecto_tecno.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyecto_tecno.Evento
import com.example.proyecto_tecno.R

class EventosViewHolder(view: View):RecyclerView.ViewHolder(view){
    val nombre = view.findViewById<TextView>(R.id.nombreEvento)
    val descripcion = view.findViewById<TextView>(R.id.descEvento)
    val ubicacion = view.findViewById<TextView>(R.id.ubiEvento)
    val foto = view.findViewById<ImageView>(R.id.imageEvento)

    fun render(eventoModel: Evento){
        nombre.text = eventoModel.nombre
        descripcion.text = eventoModel.descripcion
        ubicacion.text = eventoModel.ubicacion
        Glide.with(foto.context).load(eventoModel.foto).into(foto)
    }
}