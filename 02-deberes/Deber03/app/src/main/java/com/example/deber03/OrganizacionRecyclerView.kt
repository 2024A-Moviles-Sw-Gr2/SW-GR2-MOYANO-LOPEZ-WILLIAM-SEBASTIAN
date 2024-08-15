package com.example.deber03

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrganizacionRecyclerView(private val organizacionList: List<Organizacion>) :
    RecyclerView.Adapter<OrganizacionRecyclerView.OrganizacionViewHolder>() {

    // ViewHolder para cada ítem en la lista
    class OrganizacionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val organizacionImage: ImageView = view.findViewById(R.id.organizacion_image)
        val organizacionNombre: TextView = view.findViewById(R.id.organizacion_nombre)
        val nombreAutor: TextView = view.findViewById(R.id.nombre_autor)
    }

    // Inflar el layout para cada ítem
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganizacionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.organizacion_item, parent, false)
        return OrganizacionViewHolder(view)
    }

    // Vincular los datos a las vistas
    override fun onBindViewHolder(holder: OrganizacionViewHolder, position: Int) {
        val organizacion = organizacionList[position]
        holder.organizacionImage.setImageResource(organizacion.organizacionImage)
        holder.organizacionNombre.text = organizacion.organizacionNombre
        holder.nombreAutor.text = organizacion.nombreAutor
    }

    override fun getItemCount(): Int {
        return organizacionList.size
    }
}
