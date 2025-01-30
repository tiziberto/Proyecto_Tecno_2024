package com.example.proyecto_tecno.databse

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao  // Añadido para marcar esta interfaz como DAO
interface EventoDao {
    @Query("SELECT * FROM EventoEntity")
    fun getAllEventos(): MutableList<EventoEntity>

    @Query("SELECT id FROM EventoEntity")
    fun getAllIds(): List<Long>

    @Query("SELECT * FROM EventoEntity WHERE id = :id")
    fun getEventoById(id: Long): EventoEntity?

    @Insert
    fun addEvento(evento: EventoEntity): Long

    @Delete
    fun deleteEvento(evento: EventoEntity)

    @Dao
    interface EventoDao {
        @Query("DELETE FROM EventoEntity")
        fun deleteAllEventos()
    }

}