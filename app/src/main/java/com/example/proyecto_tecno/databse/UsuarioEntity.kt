package com.example.proyecto_tecno.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var nombre: String,
    var mail: String,
    var clave: String
)
