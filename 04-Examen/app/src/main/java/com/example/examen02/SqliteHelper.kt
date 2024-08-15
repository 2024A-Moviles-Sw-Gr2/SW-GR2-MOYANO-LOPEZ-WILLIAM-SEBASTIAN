package com.example.examen02

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.sql.Date

class SqliteHelper (
    contexto: Context?
): SQLiteOpenHelper(
    contexto,
    "examen_moviles",
    null,
    2
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
                    disponible BOOLEAN,
                    latitud DOUBLE,
                    longitud DOUBLE
                )
            """.trimIndent()

        val scriptSQLCrearTablaPasajero =
            """
                CREATE TABLE IF NOT EXISTS PASAJERO(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre VARCHAR(50),
                    fechaNacimiento DATE,
                    numeroTelefono INTEGER,
                    peso DOUBLE,
                    discapacidad BOOLEAN,
                    idAvion INTEGER,
                    FOREIGN KEY (idAvion) REFERENCES AVION(id)
                )
            """.trimIndent()

        db?.execSQL(scriptSQLCrearTablaAvion)
        db?.execSQL(scriptSQLCrearTablaPasajero)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS PASAJERO")
        db?.execSQL("DROP TABLE IF EXISTS AVION")
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Lógica para manejar la degradación
        db?.execSQL("DROP TABLE IF EXISTS PASAJERO")
        db?.execSQL("DROP TABLE IF EXISTS AVION")
        onCreate(db)
    }


    fun crearAvion(
        nombre: String,
        fechaConstruccion: Date,
        cantidadPasajeros: Int,
        pesoMaximo: Double,
        disponible: Boolean,
        latitud: Double,
        longitud: Double
    ): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("fechaConstruccion", fechaConstruccion.time)
        valoresAGuardar.put("cantidadPasajeros", cantidadPasajeros)
        valoresAGuardar.put("pesoMaximo", pesoMaximo)
        valoresAGuardar.put("disponible", if (disponible) 1 else 0)
        valoresAGuardar.put("latitud", latitud)
        valoresAGuardar.put("longitud", longitud)

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
                val latitud = cursor.getDouble(cursor.getColumnIndexOrThrow("latitud"))
                val longitud = cursor.getDouble(cursor.getColumnIndexOrThrow("longitud"))

                val avion = Avion(id, nombre, fechaConstruccion, cantidadPasajeros, pesoMaximo, disponible, latitud, longitud)
                listaAviones.add(avion)
            } while (cursor.moveToNext())
        }
        cursor.close()
        baseDatosLectura.close()
        return listaAviones
    }

    fun eliminarAvion(id: Int): Boolean {
        val baseDatosEscritura = writableDatabase
        val resultado = baseDatosEscritura.delete("AVION", "id=?", arrayOf(id.toString()))
        baseDatosEscritura.close()
        return resultado != -1
    }

    fun actualizarAvion(
        id: Int,
        nombre: String,
        fechaConstruccion: Date,
        cantidadPasajeros: Int,
        pesoMaximo: Double,
        disponible: Boolean,
        latitud: Double,
        longitud: Double
    ): Boolean {
        val db = this.writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("fechaConstruccion", fechaConstruccion.time)
        valoresAActualizar.put("cantidadPasajeros", cantidadPasajeros)
        valoresAActualizar.put("pesoMaximo", pesoMaximo)
        valoresAActualizar.put("disponible", if (disponible) 1 else 0)
        valoresAActualizar.put("latitud", latitud)
        valoresAActualizar.put("longitud", longitud)
        val resultado = db.update(
            "AVION", // Nombre de la tabla
            valoresAActualizar, // Valores a actualizar
            "id=?", // Clausula where
            arrayOf(id.toString()) // Parametros de la clausula where
        )
        db.close()
        return resultado > 0
    }

    fun crearPasajero(
        nombre: String,
        fechaNacimiento: Date,
        numeroTelefono: Int,
        peso: Double,
        discapacidad: Boolean,
        idAvion: Int
    ): Boolean{
        val baseDatoEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("fechaNacimiento", fechaNacimiento.time)
        valoresAGuardar.put("numeroTelefono", numeroTelefono)
        valoresAGuardar.put("peso", peso)
        valoresAGuardar.put("discapacidad", if (discapacidad) 1 else 0)
        valoresAGuardar.put("idAvion", idAvion)

        Log.d("SqliteHelperPasajero", "Insertando pasajero: $valoresAGuardar")
        val resultadoGuardar = baseDatoEscritura.insert("PASAJERO", null, valoresAGuardar)
        baseDatoEscritura.close()
        return resultadoGuardar != -1L
    }

    fun obtenerPasajeros(): MutableList<Pasajero>{
        val listaPasajeros: MutableList<Pasajero> = mutableListOf()
        val baseDatosLectura = readableDatabase
        val query = "SELECT * FROM PASAJERO"
        val cursor = baseDatosLectura.rawQuery(query, null)
        if (cursor.moveToFirst()){
            do{
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val fechaNacimiento = Date(cursor.getLong(cursor.getColumnIndexOrThrow("fechaNacimiento")))
                val numeroTelefono = cursor.getInt(cursor.getColumnIndexOrThrow("numeroTelefono"))
                val peso = cursor.getDouble(cursor.getColumnIndexOrThrow("peso"))
                val disponible = cursor.getInt(cursor.getColumnIndexOrThrow("discapacidad")) == 1

                val pasajero = Pasajero(id, nombre, fechaNacimiento, numeroTelefono, peso, disponible)
                listaPasajeros.add(pasajero)
            } while (cursor.moveToNext())
        }
        cursor.close()
        baseDatosLectura.close()
        return listaPasajeros
    }

    fun eliminarPasajero(id: Int): Boolean{
        val baseDatosEscritura = writableDatabase
        val resultado = baseDatosEscritura.delete("PASAJERO", "id=?", arrayOf(id.toString()))
        baseDatosEscritura.close()
        return resultado != -1
    }

    fun actualizarPasajero(
        id: Int,
        nombre: String,
        fechaNacimiento: Date,
        numeroTelefono: Int,
        peso: Double,
        discapacidad: Boolean
    ): Boolean{
        val db  = this.writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("fechaNacimiento", fechaNacimiento.time)
        valoresAActualizar.put("numeroTelefono", numeroTelefono)
        valoresAActualizar.put("peso", peso)
        valoresAActualizar.put("discapacidad", if (discapacidad) 1 else 0)
        val resultado = db.update(
            "PASAJERO",
            valoresAActualizar,
            "id=?",
            arrayOf(id.toString())
        )
        db.close()
        return resultado > 0
    }

    fun obtenerPasajerosPorAvion(idAvion: Int): MutableList<Pasajero> {
        val listaPasajeros: MutableList<Pasajero> = mutableListOf()
        val baseDatosLectura = readableDatabase
        val query = "SELECT * FROM PASAJERO WHERE idAvion = ?"
        val cursor = baseDatosLectura.rawQuery(query, arrayOf(idAvion.toString()))

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val fechaNacimiento = Date(cursor.getLong(cursor.getColumnIndexOrThrow("fechaNacimiento")))
                val numeroTelefono = cursor.getInt(cursor.getColumnIndexOrThrow("numeroTelefono"))
                val peso = cursor.getDouble(cursor.getColumnIndexOrThrow("peso"))
                val disponible = cursor.getInt(cursor.getColumnIndexOrThrow("discapacidad")) == 1

                val pasajero = Pasajero(id, nombre, fechaNacimiento, numeroTelefono, peso, disponible)
                listaPasajeros.add(pasajero)
            } while (cursor.moveToNext())
        } else {
            Log.d("SqliteHelper", "No se encontraron pasajeros con idAvion = $idAvion")
        }

        cursor.close()
        baseDatosLectura.close()
        return listaPasajeros
    }


}
