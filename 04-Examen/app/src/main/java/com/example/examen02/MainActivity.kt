package com.example.examen02

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var agregarAvionLauncher: ActivityResultLauncher<Intent>
    private lateinit var verPasajerosLauncher: ActivityResultLauncher<Intent>
    private lateinit var adaptador: ArrayAdapter<Avion>
    private lateinit var aviones: MutableList<Avion>
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
        BaseDatos.tabla = SqliteHelper(this)
        val listView = findViewById<ListView>(R.id.lv_lista_aviones)
        aviones = BaseDatos.tabla!!.obtenerAviones()

        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            aviones
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        registerForContextMenu(listView)

        val botonAgregarAvion = findViewById<Button>(R.id.btn_agregar_avion)
        botonAgregarAvion.setOnClickListener {
            irActividad(AgregarActualizarAvionActivity::class.java)
        }

        // Registrar el lanzador para obtener el resultado
        agregarAvionLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                // Actualizar la lista de aviones
                aviones.clear()
                aviones.addAll(BaseDatos.tabla!!.obtenerAviones())
                adaptador.notifyDataSetChanged()
            }
        }

        verPasajerosLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            // Aquí podrías manejar el resultado si es necesario
        }

    }

    var posicionItemSeleccionado = -1
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        var inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_editar_avion -> {
                val avion = aviones[posicionItemSeleccionado]
                val intent = Intent(this, AgregarActualizarAvionActivity::class.java)
                intent.putExtra("avion", avion)
                agregarAvionLauncher.launch(intent)
                return true
            }
            R.id.mi_borrar_avion -> {
                val avionAEliminar = aviones[posicionItemSeleccionado]
                val exito = BaseDatos.tabla?.eliminarAvion(avionAEliminar.id)
                if(exito == true){
                    aviones.removeAt(posicionItemSeleccionado)
                    adaptador.notifyDataSetChanged()
                    Toast.makeText(this, "Avion eliminado", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "Error al eliminar el avion", Toast.LENGTH_SHORT).show()
                }
                return true
            }
            R.id.mi_ver_pasajeros -> {
                val avion = aviones[posicionItemSeleccionado]
                val intent = Intent(this, ListaPasajerosActivity::class.java)
                intent.putExtra("idAvion", avion.id)
                verPasajerosLauncher.launch(intent)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        agregarAvionLauncher.launch(intent)
    }
}