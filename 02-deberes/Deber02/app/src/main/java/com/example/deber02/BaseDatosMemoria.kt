package com.example.deber02

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BaseDatosMemoria {
    companion object{
        var arregloAvion = arrayListOf<Avion>()
        @SuppressLint("ConstantLocale")
        val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        init {
            arregloAvion.add(
                Avion(1, "Avianca", format.parse("20/12/2020"), 100, 4500.2, true)
            )
            arregloAvion.add(
                Avion(2,"Avion2", format.parse("12-03-2021"), 400, 40000.2, true)
            )
        }
    }
}