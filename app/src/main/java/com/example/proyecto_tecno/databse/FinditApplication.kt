package com.example.proyecto_tecno.databse
import android.app.Application
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FinditApplication : Application() {

    companion object {
        lateinit var dataBase: FindItDataBase
    }

    override fun onCreate() {
        super.onCreate()

        // Inicialización en un hilo de fondo usando Corrutinas
        CoroutineScope(Dispatchers.IO).launch {
            dataBase = Room.databaseBuilder(
                applicationContext,
                FindItDataBase::class.java, "FindItDatabase"
            ).build()
        }

//        // Inicializar en el hilo principal (usar si falla en corrutinas)
//        dataBase = Room.databaseBuilder(
//            applicationContext,
//            FindItDataBase::class.java, "FindItDatabase"
//        )
//            .fallbackToDestructiveMigration()
//            .build()
    }
}