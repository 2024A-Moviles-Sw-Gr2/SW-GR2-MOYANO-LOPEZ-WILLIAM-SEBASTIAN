package com.example.deber02

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

class AgregarAvion : AppCompatActivity() {
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_avion)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val botonAgregarAvion = findViewById<Button>(R.id.btn_aceptar_agregar_avion)
        botonAgregarAvion.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.input_nombre_avion).text.toString()
            val fechaConstruccionStr = findViewById<EditText>(R.id.input_fechaConstruccion_avion).text.toString()
            val cantidadPasajerosStr = findViewById<EditText>(R.id.input_cantidadPasajeros_avion).text.toString()
            val pesoMaximoStr = findViewById<EditText>(R.id.input_pesoMaximo_avion).text.toString()
            val disponibilidadStr = findViewById<Spinner>(R.id.spinner_disponibilidad_avion).selectedItem.toString()

            val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val fechaConstruccionDate = formatter.parse(fechaConstruccionStr)

            if (nombre.isNotEmpty() && fechaConstruccionDate != null && cantidadPasajerosStr.isNotEmpty() && pesoMaximoStr.isNotEmpty()) {
                val cantidadPasajeros = cantidadPasajerosStr.toIntOrNull()
                val pesoMaximo = pesoMaximoStr.toDoubleOrNull()
                val disponibilidad = disponibilidadStr == "Disponible"

                if (cantidadPasajeros != null && pesoMaximo != null) {
                    val fechaConstruccionSqlDate = Date(fechaConstruccionDate.time)

                    val respuesta = BaseDatos.tablaAvion?.crearAvion(
                        nombre,
                        fechaConstruccionSqlDate,
                        cantidadPasajeros,
                        pesoMaximo,
                        disponibilidad
                    )

                    if (respuesta == true) {
                        Toast.makeText(this, "Avión agregado correctamente", Toast.LENGTH_SHORT).show()
                        val intent = Intent()
                        intent.putExtra("nombre", nombre)
                        setResult(RESULT_OK, intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Error al agregar el avión", Toast.LENGTH_SHORT).show()
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
                    textView.setTextColor(android.graphics.Color.BLACK)
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
                    Toast.makeText(this@AgregarAvion, "Seleccionado: $selectedItem", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        spinner.setSelection(0)
    }
}
