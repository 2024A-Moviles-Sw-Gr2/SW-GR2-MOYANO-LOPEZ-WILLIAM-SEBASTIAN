package com.example.deber02

import android.os.Parcel
import android.os.Parcelable
import java.util.Date

class Pasajero (
    private var id: Int,
    private var nombre: String,
    private var fechaNacimiento: Date,
    private var primeraClase: Boolean,
    private var numeroTelefono: Int,
    private var peso: Double
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        Date(parcel.readLong()),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeLong(fechaNacimiento.time)
        parcel.writeByte(if (primeraClase) 1 else 0)
        parcel.writeInt(numeroTelefono)
        parcel.writeDouble(peso)
    }

    override fun describeContents(): Int {
        return 0
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