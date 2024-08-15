package com.example.examen02

import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Avion (
    var id: Int,
    var nombre: String,
    var fechaConstruccion: Date,
    var cantidadPasajeros: Int,
    var pesoMaximo: Double,
    var disponible: Boolean,
    var latitud: Double,
    var longitud: Double
): Parcelable{

    constructor(parcel: Parcel): this(
        parcel.readInt(),
        parcel.readString()!!,
        Date(parcel.readLong()),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readByte() != 0.toByte(),
        parcel.readDouble(),
        parcel.readDouble()
    )


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeLong(fechaConstruccion.time)
        parcel.writeInt(cantidadPasajeros)
        parcel.writeDouble(pesoMaximo)
        parcel.writeByte(if (disponible) 1 else 0)
        parcel.writeDouble(latitud)
        parcel.writeDouble(longitud)
    }

    override fun toString(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaFormateada = formatter.format(fechaConstruccion)
        return "Nombre: $nombre\n" +
                "Fecha Construccion: $fechaFormateada\n" +
                "Cantidad Pasajeros: $cantidadPasajeros\n" +
                "Peso Maximo: $pesoMaximo\n" +
                "Disponible: $disponible\n" +
                "Latitud: $latitud\n" +
                "Longitud: $longitud\n"
    }
    companion object CREATOR : Parcelable.Creator<Avion> {
        override fun createFromParcel(parcel: Parcel): Avion {
            return Avion(parcel)
        }

        override fun newArray(size: Int): Array<Avion?> {
            return arrayOfNulls(size)
        }
    }
}