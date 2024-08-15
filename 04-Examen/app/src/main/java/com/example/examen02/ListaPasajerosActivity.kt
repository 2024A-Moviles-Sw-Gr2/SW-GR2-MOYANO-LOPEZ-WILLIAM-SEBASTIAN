package com.example.examen02

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

class ListaPasajerosActivity : AppCompatActivity() {
    private lateinit var pasajeros: MutableList<Pasajero>
    private lateinit var adaptador: ArrayAdapter<Pasajero>
    private lateinit var agregarPasajeroLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lista_pasajeros)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val idAvion = intent.getIntExtra("idAvion", -1)
        BaseDatos.tabla = SqliteHelper(this)
        val listView = findViewById<ListView>(R.id.lv_lista_pasajeros)
        pasajeros = BaseDatos.tabla!!.obtenerPasajerosPorAvion(idAvion)

        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            pasajeros
        )

        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        registerForContextMenu(listView)

        val botonAgregarPasajero = findViewById<Button>(R.id.btn_agregar_pasajero)
        botonAgregarPasajero.setOnClickListener {
            val intent = Intent(this, AgregarActualizarPasajeroActivity::class.java)
            intent.putExtra("idAvion", idAvion)
            agregarPasajeroLauncher.launch(intent)
            true
        }


        agregarPasajeroLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                // Actualizar la lista de aviones
                pasajeros.clear()
                pasajeros.addAll(BaseDatos.tabla!!.obtenerPasajerosPorAvion(idAvion))
                adaptador.notifyDataSetChanged()
            }
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
        inflater.inflate(R.menu.menu_pasajero, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_editar_pasajero -> {
                val pasajero = pasajeros[posicionItemSeleccionado]
                val intent = Intent(this, AgregarActualizarPasajeroActivity::class.java)
                intent.putExtra("pasajero", pasajero)
                agregarPasajeroLauncher.launch(intent)
                return true
            }
            R.id.mi_borrar_pasajero -> {
                val pasajeroAEliminar = pasajeros[posicionItemSeleccionado]
                val exito = BaseDatos.tabla?.eliminarPasajero(pasajeroAEliminar.id)
                if(exito == true){
                    pasajeros.removeAt(posicionItemSeleccionado)
                    adaptador.notifyDataSetChanged()
                    Toast.makeText(this, "Pasajero eliminado", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "Error al eliminar el pasajero", Toast.LENGTH_SHORT).show()
                }
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        agregarPasajeroLauncher.launch(intent)
    }
}