package com.example.proyecto_tecno.databse

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Locale

@Entity(tableName = "EventoEntity")
data class EventoEntity(

    @PrimaryKey val id: Long = 0,
    val nombre: String,
    val descripcion: String,
    val ubicacion: String,
    val foto: String,
    val fecha : String
)
    : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(nombre)
        parcel.writeString(descripcion)
        parcel.writeString(ubicacion)
        parcel.writeString(foto)
        parcel.writeString(fecha)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EventoEntity> {
        override fun createFromParcel(parcel: Parcel): EventoEntity {
            return EventoEntity(parcel)
        }

        override fun newArray(size: Int): Array<EventoEntity?> {
            return arrayOfNulls(size)
        }
    }
}