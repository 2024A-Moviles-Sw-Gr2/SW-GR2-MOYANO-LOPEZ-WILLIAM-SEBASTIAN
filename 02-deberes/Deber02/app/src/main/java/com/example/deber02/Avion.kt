package com.example.deber02

import android.os.Parcel
import android.os.Parcelable
import java.util.Date

class Avion (
    var id: Int,
    var nombre: String,
    var fechaConstruccion: Date,
    var cantidadPasajeros: Int,
    var pesoMaximo: Double,
    var disponible: Boolean
): Parcelable{

    constructor(parcel: Parcel): this(
        parcel.readInt(),
        parcel.readString()!!,
        Date(parcel.readLong()),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readByte() != 0.toByte()
    ){

    }
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