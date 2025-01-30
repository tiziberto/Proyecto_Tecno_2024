package com.example.proyecto_tecno.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.proyecto_tecno.models.UsuarioEntity

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "usuarios.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE IF NOT EXISTS usuario (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                mail TEXT UNIQUE,
                contraseña TEXT
            );
        """
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS usuario")
        onCreate(db)
    }

    // Método para registrar un usuario
    fun registrarUsuario(usuario: UsuarioEntity): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("nombre", usuario.nombre)
            put("mail", usuario.mail)
            put("contraseña", usuario.clave)
        }

        // Insertar el usuario y verificar si fue exitoso
        val result = db.insert("usuario", null, values)
        db.close()
        return result != -1L // Si el valor es -1, significa que falló la inserción
    }
}
