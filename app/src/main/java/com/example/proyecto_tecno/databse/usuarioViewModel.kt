//package com.example.proyecto_tecno.viewmodels
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import com.example.proyecto_tecno.database.AppDatabase
//import com.example.proyecto_tecno.models.UsuarioEntity
//
//class UsuarioViewModel(application: Application) : AndroidViewModel(application) {
//
//    private val usuarioDao = AppDatabase.getDatabase(application).usuarioDao()
//
//    // Buscar usuario por email y contraseña
//    suspend fun getUsuarioByEmailAndPassword(email: String, password: String): UsuarioEntity? {
//        return usuarioDao.getUsuarioByEmailAndPassword(email, password)
//    }
//}
