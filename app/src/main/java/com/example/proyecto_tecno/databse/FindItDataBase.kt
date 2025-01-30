package com.example.proyecto_tecno.databse

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.proyecto_tecno.models.UsuarioEntity   // Asegúrate de importar UsuarioEntity correctamente
import com.example.proyecto_tecno.databse.EventoEntity
import com.example.proyecto_tecno.databse.EventoDao
import com.example.proyecto_tecno.database.UsuarioDao

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Aquí defines lo que debe cambiar, por ejemplo, agregar una nueva columna:
        // database.execSQL("ALTER TABLE UsuarioEntity ADD COLUMN new_column INTEGER NOT NULL DEFAULT 0")
    }
}

@Database(entities = [EventoEntity::class, UsuarioEntity::class], version = 2)
abstract class FindItDataBase : RoomDatabase() {
    abstract fun EventoDao(): EventoDao
    abstract fun UsuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var INSTANCE: FindItDataBase? = null

        fun getDatabase(context: Context): FindItDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FindItDataBase::class.java,
                    "find_it_database"
                )
                    .addMigrations(MIGRATION_1_2)  // Añadir migración aquí
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}