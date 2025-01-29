package com.example.proyecto_tecno

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao  // Añadido para marcar esta interfaz como DAO
interface EventoDao {
    @Query("SELECT * FROM EventoEntity")
    fun getAllEventos(): MutableList<Evento>

    @Insert
    fun addEvento(evento: Evento): Long
}