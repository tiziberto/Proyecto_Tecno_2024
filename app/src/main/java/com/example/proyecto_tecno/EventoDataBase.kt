package com.example.proyecto_tecno

import androidx.room.Database
import androidx.room.RoomDatabase


@Database (entities = [Evento::class], version = 1 )
abstract class EventoDataBase : RoomDatabase() {
    abstract fun EventoDao(): EventoDao
}