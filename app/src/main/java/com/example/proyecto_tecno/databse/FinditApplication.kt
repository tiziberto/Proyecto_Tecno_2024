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

    dataBase = Room.databaseBuilder(
            applicationContext,
            FindItDataBase::class.java, "FindItDatabase"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}