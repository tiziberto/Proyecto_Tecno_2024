package com.example.proyecto_tecno

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "EventoEntity")
data class Evento(
    @PrimaryKey val id: Long = 0,
    val nombre: String,
    val descripcion: String,
    val ubicacion: String,
    val foto: String
)
    : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong()!!,
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
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Evento> {
        override fun createFromParcel(parcel: Parcel): Evento {
            return Evento(parcel)
        }

        override fun newArray(size: Int): Array<Evento?> {
            return arrayOfNulls(size)
        }
    }
}