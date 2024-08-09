package com.example.deber02

import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Pasajero (
    var id: Int,
    var nombre: String,
    var fechaNacimiento: Date,
    var numeroTelefono: Int,
    var peso: Double,
    var discapacidad: Boolean
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        Date(parcel.readLong()),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeLong(fechaNacimiento.time)
        parcel.writeInt(numeroTelefono)
        parcel.writeDouble(peso)
        parcel.writeByte(if (discapacidad) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaFormateada = formatter.format(fechaNacimiento)
        return "Nombre: $nombre\n" +
                "Fecha Nacimiento: $fechaFormateada\n" +
                "Numero telefono: $numeroTelefono\n" +
                "Peso: $peso\n" +
                "Dispacidad: $discapacidad"
    }

    companion object CREATOR : Parcelable.Creator<Pasajero> {
        override fun createFromParcel(parcel: Parcel): Pasajero {
            return Pasajero(parcel)
        }

        override fun newArray(size: Int): Array<Pasajero?> {
            return arrayOfNulls(size)
        }
    }

}