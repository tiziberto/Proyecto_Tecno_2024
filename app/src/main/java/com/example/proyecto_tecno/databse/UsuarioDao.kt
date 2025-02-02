package com.example.proyecto_tecno.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.proyecto_tecno.models.UsuarioEntity

@Dao
interface UsuarioDao {

    @Insert
    suspend fun insertUsuario(usuario: UsuarioEntity): Long

    @Query("SELECT * FROM usuario WHERE mail = :email AND clave = :password LIMIT 1")
    suspend fun getUsuarioByEmailAndPassword(email: String, password: String): UsuarioEntity?
}

