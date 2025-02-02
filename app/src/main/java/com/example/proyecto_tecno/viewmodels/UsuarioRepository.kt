package com.example.proyecto_tecno.database

import com.example.proyecto_tecno.models.UsuarioEntity

class UsuarioRepository(private val usuarioDao: UsuarioDao) {

    suspend fun getUsuarioByEmailAndPassword(email: String, password: String): UsuarioEntity? {
        return usuarioDao.getUsuarioByEmailAndPassword(email, password)
    }
}
