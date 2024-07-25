package com.example.deber02

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.sql.Date

class SqliteHelperAvion (
    contexto: Context?
): SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaAvion =
            """
                CREATE TABLE AVION(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre VARCHAR(50),
                    fechaConstruccion DATE,
                    cantidadPasajeros INTEGER,
                    pesoMaximo DOUBLE,
                    disponible BOOLEAN
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaAvion)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Aquí puedes manejar las actualizaciones de la base de datos
    }

    fun crearAvion(
        nombre: String,
        fechaConstruccion: Date,
        cantidadPasajeros: Int,
        pesoMaximo: Double,
        disponible: Boolean
    ): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("fechaConstruccion", fechaConstruccion.time)
        valoresAGuardar.put("cantidadPasajeros", cantidadPasajeros)
        valoresAGuardar.put("pesoMaximo", pesoMaximo)
        valoresAGuardar.put("disponible", if (disponible) 1 else 0)

        Log.d("SqliteHelperAvion", "Insertando avión: $valoresAGuardar")
        val resultadoGuardar = baseDatosEscritura.insert("AVION", null, valoresAGuardar)
        baseDatosEscritura.close()
        return resultadoGuardar != -1L
    }

    fun obtenerAviones(): MutableList<Avion> {
        val listaAviones: MutableList<Avion> = mutableListOf()
        val baseDatosLectura = readableDatabase
        val query = "SELECT * FROM AVION"
        val cursor = baseDatosLectura.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val fechaConstruccion = Date(cursor.getLong(cursor.getColumnIndexOrThrow("fechaConstruccion")))
                val cantidadPasajeros = cursor.getInt(cursor.getColumnIndexOrThrow("cantidadPasajeros"))
                val pesoMaximo = cursor.getDouble(cursor.getColumnIndexOrThrow("pesoMaximo"))
                val disponible = cursor.getInt(cursor.getColumnIndexOrThrow("disponible")) == 1

                val avion = Avion(id, nombre, fechaConstruccion, cantidadPasajeros, pesoMaximo, disponible)
                listaAviones.add(avion)
            } while (cursor.moveToNext())
        }
        cursor.close()
        baseDatosLectura.close()
        return listaAviones
    }
}
