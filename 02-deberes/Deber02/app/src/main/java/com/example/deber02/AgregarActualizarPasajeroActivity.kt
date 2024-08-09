package com.example.deber02

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

class AgregarActualizarPasajeroActivity : AppCompatActivity() {
    private var pasajeroId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_actualizar_pasajero)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nombreEditText = findViewById<EditText>(R.id.input_nombre_pasajero)
        val fechaNacimientoEditText = findViewById<EditText>(R.id.input_fecha_nacimiento_pasajero)
        val numeroTelefonoEditText = findViewById<EditText>(R.id.input_numero_telefono_pasajero)
        val pesoEditText = findViewById<EditText>(R.id.input_peso_pasajero)
        val discapacidadSpinner = findViewById<Spinner>(R.id.spinner_discapacidad_pasajero)
        val botonAgregarPasajero = findViewById<Button>(R.id.btn_aceptar_agregar_pasajero)

        // Verificar si estamos en modo de edición
        if (intent.hasExtra("pasajero")) {
            val pasajero = intent.getParcelableExtra<Pasajero>("pasajero")
            pasajero?.let {
                pasajeroId = it.id
                nombreEditText.setText(it.nombre)
                val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                fechaNacimientoEditText.setText(formatter.format(it.fechaNacimiento))
                numeroTelefonoEditText.setText(it.numeroTelefono.toString())
                pesoEditText.setText(it.peso.toString())
                discapacidadSpinner.setSelection(if (it.discapacidad) 1 else 2)
                botonAgregarPasajero.text = "Guardar cambios"
            }
        }

        botonAgregarPasajero.setOnClickListener {
            val nombre = nombreEditText.text.toString()
            val fechaNacimientoStr = fechaNacimientoEditText.text.toString()
            val numeroTelefonoStr = numeroTelefonoEditText.text.toString()
            val pesoStr = pesoEditText.text.toString()
            val discapacidadStr = discapacidadSpinner.selectedItem.toString()

            val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val fechaNacimientoDate = formatter.parse(fechaNacimientoStr)

            if (nombre.isNotEmpty() && fechaNacimientoDate != null && numeroTelefonoStr.isNotEmpty() && pesoStr.isNotEmpty()) {
                val numeroTelefono = numeroTelefonoStr.toIntOrNull()
                val peso = pesoStr.toDoubleOrNull()
                val discapacidad = discapacidadStr == "Disponible"

                if (numeroTelefono != null && peso != null) {
                    val fechaNacimientoSqlDate = Date(fechaNacimientoDate.time)

                    val respuesta = if (pasajeroId == null) {
                        BaseDatos.tabla?.crearPasajero(
                            nombre,
                            fechaNacimientoSqlDate,
                            numeroTelefono,
                            peso,
                            discapacidad
                        )
                    } else {
                        BaseDatos.tabla?.actualizarPasajero(
                            pasajeroId!!,
                            nombre,
                            fechaNacimientoSqlDate,
                            numeroTelefono,
                            peso,
                            discapacidad
                        )
                    }

                    if (respuesta == true) {
                        Toast.makeText(this, if (pasajeroId == null) "Pasajero agregado correctamente" else "Pasajero actualizado correctamente", Toast.LENGTH_SHORT).show()
                        val intent = Intent()
                        intent.putExtra("nombre", nombre)
                        setResult(RESULT_OK, intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Error al " + if (pasajeroId == null) "agregar" else "actualizar" + " el pasajero", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Datos inválidos", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Configuración del spinner
        val spinner: Spinner = findViewById(R.id.spinner_discapacidad_pasajero)
        val spinnerItems = resources.getStringArray(R.array.s_seleccionar_discapacidad)
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
                    Toast.makeText(this@AgregarActualizarPasajeroActivity, "Seleccionado: $selectedItem", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        spinner.setSelection(0)

    }
}