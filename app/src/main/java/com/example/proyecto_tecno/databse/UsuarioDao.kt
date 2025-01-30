package com.example.proyecto_tecno.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.proyecto_tecno.models.UsuarioEntity

@Dao
interface UsuarioDao {

    @Insert
    suspend fun insertUsuario(usuario: UsuarioEntity): Long


//    @Query("SELECT * FROM UsuarioEntity WHERE mail = :mail LIMIT 1")
//    fun getUsuarioByEmail(email: String): UsuarioEntity?
}
