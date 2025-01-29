package com.example.proyecto_tecno
import android.app.Application
import androidx.room.Room
import com.example.proyecto_tecno.EventoDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FinditApplication : Application() {

    companion object {
        lateinit var dataBase: EventoDataBase
    }

    override fun onCreate() {
        super.onCreate()

        // Inicialización de la base de datos en un hilo de fondo usando Coroutines
        CoroutineScope(Dispatchers.IO).launch {
            dataBase = Room.databaseBuilder(
                applicationContext,
                EventoDataBase::class.java, "FindItDatabase"
            ).build()
        }
    }
}