package com.example.deber02

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var agregarAvionLauncher: ActivityResultLauncher<Intent>
    private lateinit var adaptador: ArrayAdapter<String>
    private lateinit var aviones: MutableList<String>
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Inicializar base de datos
        BaseDatos.tablaAvion = SqliteHelperAvion(this)
        val listView = findViewById<ListView>(R.id.lv_lista_aviones)
        aviones = BaseDatos.tablaAvion!!.obtenerAviones()

        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            aviones
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()


        val botonAgregarAvion = findViewById<Button>(R.id.btn_agregar_avion)
        botonAgregarAvion.setOnClickListener {
            irActividad(AgregarAvion::class.java)
        }

        // Registrar el lanzador para obtener el resultado
        agregarAvionLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                // Obtener el nombre del avión del Intent
                val nombre = result.data?.getStringExtra("nombre")
                if (nombre != null) {
                    // Agregar el nuevo avión a la lista y actualizar el adaptador
                    aviones.add(nombre)
                    adaptador.notifyDataSetChanged()
                }
            }
        }
    }

    fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        agregarAvionLauncher.launch(intent)
    }
}