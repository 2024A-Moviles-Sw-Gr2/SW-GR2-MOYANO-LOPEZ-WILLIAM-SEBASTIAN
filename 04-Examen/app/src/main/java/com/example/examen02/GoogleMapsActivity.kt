package com.example.examen02

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class GoogleMapsActivity : AppCompatActivity() {
    private lateinit var mapa: GoogleMap
    var permisos = false
    var avion: Avion? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_google_maps)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        avion = intent.getParcelableExtra<Avion>("avion")
        solicitarPermisos()
        iniciarLogicaMapa()

    }

    private fun iniciarLogicaMapa() {
        val fragmentoMapa = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        fragmentoMapa.getMapAsync{googleMap ->
            with(googleMap){
                mapa = googleMap
                establecerConfiguracionMapa()
                moverDireccion()
            }

        }
    }

    private fun moverDireccion() {
        avion?.let {
            val ubicacion = LatLng(it.latitud, it.longitud)
            val nombreAvion = it.nombre

            val mark = anadirMarcador(ubicacion, nombreAvion)
            mark?.tag = nombreAvion

            moverCamaraConZoom(ubicacion)
        }
    }


    fun moverCamaraConZoom(latLang: LatLng, zoom: Float=20f){
        mapa.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                latLang,zoom
            )
        )
    }

    fun anadirMarcador(latLang: LatLng, title:String): Marker {
        return mapa.addMarker(
            MarkerOptions().position(latLang).title(title)
        )!!
    }

    private fun establecerConfiguracionMapa() {
        val contexto = this.applicationContext
        with(mapa){
            val nombrePermisoFine = android.Manifest.permission.ACCESS_FINE_LOCATION
            val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION
            val permisoFine = ContextCompat.checkSelfPermission(contexto, nombrePermisoFine)
            val permisoCoarse = ContextCompat.checkSelfPermission(contexto, nombrePermisoCoarse)
            val tienePermisos = permisoFine == PackageManager.PERMISSION_GRANTED &&
                    permisoCoarse == PackageManager.PERMISSION_GRANTED
            if(tienePermisos){
                mapa.isMyLocationEnabled = true
                uiSettings.isMyLocationButtonEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
        }
    }

    private fun solicitarPermisos() {
        val contexto = this.applicationContext
        val nombrePermisoFine = android.Manifest.permission.ACCESS_FINE_LOCATION
        val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION
        val permisoFine = ContextCompat.checkSelfPermission(contexto,nombrePermisoFine)
        val permisoCoarse = ContextCompat.checkSelfPermission(contexto,nombrePermisoCoarse)
        val tienePermisos = permisoFine == PackageManager.PERMISSION_GRANTED && permisoCoarse == PackageManager.PERMISSION_GRANTED

        if(tienePermisos){
            permisos=true
        }else{
            ActivityCompat.requestPermissions(
                this, arrayOf(nombrePermisoFine, nombrePermisoCoarse), 1
            )
        }
    }
}