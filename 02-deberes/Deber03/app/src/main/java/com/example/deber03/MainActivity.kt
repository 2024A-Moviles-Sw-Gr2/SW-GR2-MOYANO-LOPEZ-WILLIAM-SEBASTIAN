package com.example.deber03

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val botonRepositorio = findViewById<LinearLayout>(R.id.opcion_repositorios)
        botonRepositorio.setOnClickListener{
            val intent = Intent(this, RepositorioActivity::class.java)
            startActivity(intent)
        }

        val botonOrganizacion = findViewById<LinearLayout>(R.id.opcion_organizacion)
        botonOrganizacion.setOnClickListener{
            val intent = Intent(this, OrganizacionActivity::class.java)
            startActivity(intent)
        }
    }
}