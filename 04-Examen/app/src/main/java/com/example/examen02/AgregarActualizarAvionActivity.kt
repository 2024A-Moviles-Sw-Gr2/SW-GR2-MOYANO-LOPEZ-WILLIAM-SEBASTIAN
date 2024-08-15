package com.example.examen02

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale

class AgregarActualizarAvionActivity : AppCompatActivity() {
    private var avionId: Int? = null
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_actualizar_avion)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nombreEditText = findViewById<EditText>(R.id.input_nombre_avion)
        val fechaConstruccionEditText = findViewById<EditText>(R.id.input_fechaConstruccion_avion)
        val cantidadPasajerosEditText = findViewById<EditText>(R.id.input_cantidadPasajeros_avion)
        val pesoMaximoEditText = findViewById<EditText>(R.id.input_pesoMaximo_avion)
        val disponibilidadSpinner = findViewById<Spinner>(R.id.spinner_disponibilidad_avion)
        val latitudEditText = findViewById<EditText>(R.id.input_latitud)
        val longitudEditText = findViewById<EditText>(R.id.input_longitud)
        val botonAgregarAvion = findViewById<Button>(R.id.btn_aceptar_agregar_avion)


        // Verificar si estamos en modo de edición
        if (intent.hasExtra("avion")) {
            val avion = intent.getParcelableExtra<Avion>("avion")
            avion?.let {
                avionId = it.id
                nombreEditText.setText(it.nombre)
                val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                fechaConstruccionEditText.setText(formatter.format(it.fechaConstruccion))
                cantidadPasajerosEditText.setText(it.cantidadPasajeros.toString())
                pesoMaximoEditText.setText(it.pesoMaximo.toString())
                disponibilidadSpinner.setSelection(if (it.disponible) 1 else 2)
                latitudEditText.setText(it.latitud.toString())
                longitudEditText.setText(it.longitud.toString())
                botonAgregarAvion.text = "Guardar Cambios"
            }
        }

        botonAgregarAvion.setOnClickListener {
            val nombre = nombreEditText.text.toString()
            val fechaConstruccionStr = fechaConstruccionEditText.text.toString()
            val cantidadPasajerosStr = cantidadPasajerosEditText.text.toString()
            val pesoMaximoStr = pesoMaximoEditText.text.toString()
            val disponibilidadStr = disponibilidadSpinner.selectedItem.toString()
            val latitudStr = latitudEditText.text.toString()
            val longitudStr = longitudEditText.text.toString()

            val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val fechaConstruccionDate = formatter.parse(fechaConstruccionStr)

            if (nombre.isNotEmpty() && fechaConstruccionDate != null && cantidadPasajerosStr.isNotEmpty() && pesoMaximoStr.isNotEmpty() && latitudStr.isNotEmpty() && longitudStr.isNotEmpty()) {
                val cantidadPasajeros = cantidadPasajerosStr.toIntOrNull()
                val pesoMaximo = pesoMaximoStr.toDoubleOrNull()
                val disponibilidad = disponibilidadStr == "Disponible"
                val latitud = latitudStr.toDoubleOrNull() ?: 0.0
                val longitud = longitudStr.toDoubleOrNull() ?: 0.0

                if (cantidadPasajeros != null && pesoMaximo != null) {
                    val fechaConstruccionSqlDate = Date(fechaConstruccionDate.time)

                    val respuesta = if (avionId == null) {
                        BaseDatos.tabla?.crearAvion(
                            nombre,
                            fechaConstruccionSqlDate,
                            cantidadPasajeros,
                            pesoMaximo,
                            disponibilidad,
                            latitud,
                            longitud
                        )
                    } else {
                        BaseDatos.tabla?.actualizarAvion(
                            avionId!!,
                            nombre,
                            fechaConstruccionSqlDate,
                            cantidadPasajeros,
                            pesoMaximo,
                            disponibilidad,
                            latitud,
                            longitud
                        )
                    }

                    if (respuesta == true) {
                        Toast.makeText(this, if (avionId == null) "Avión agregado correctamente" else "Avión actualizado correctamente", Toast.LENGTH_SHORT).show()
                        val intent = Intent()
                        intent.putExtra("nombre", nombre)
                        setResult(RESULT_OK, intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Error al " + if (avionId == null) "agregar" else "actualizar" + " el avión", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Datos inválidos", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Configuración del spinner
        val spinner: Spinner = findViewById(R.id.spinner_disponibilidad_avion)
        val spinnerItems = resources.getStringArray(R.array.s_seleccionar_dispnibilidad)
        val adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerItems) {
            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: android.view.ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                val textView = view as android.widget.TextView
                if (position == 0) {
                    textView.setTextColor(android.graphics.Color.GRAY)
                } else {
                    textView.setTextColor(android.graphics.Color.WHITE)
                }
                return view
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position != 0) {
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    Toast.makeText(this@AgregarActualizarAvionActivity, "Seleccionado: $selectedItem", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        spinner.setSelection(0)


    }
}
