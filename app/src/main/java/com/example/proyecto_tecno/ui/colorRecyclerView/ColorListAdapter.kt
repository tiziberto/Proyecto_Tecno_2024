package com.example.proyecto_tecno.ui.colorRecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_tecno.R

class ColorListAdapter (val data: List<Color>) : RecyclerView.Adapter<ColorViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.color_view,parent,false)
        return ColorViewHolder(row)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val color = data.get(position)
        holder.colorTitle.setText(color.name)
        holder.colorSubtitle.setText(color.hex)
    }

}

class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    var color : TextView = itemView.findViewById(R.id.cuadrado)
    var colorTitle : TextView = itemView.findViewById(R.id.titulo)
    var colorSubtitle : TextView = itemView.findViewById(R.id.subtitulo)

}