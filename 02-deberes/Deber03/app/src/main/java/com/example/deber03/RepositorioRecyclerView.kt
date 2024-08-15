package com.example.deber03

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RepositorioRecyclerView(private val repositoryList: List<Repositorio>) :
    RecyclerView.Adapter<RepositorioRecyclerView.RepositoryViewHolder>() {

    class RepositoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val authorImage: ImageView = view.findViewById(R.id.repository_author_image)
        val repositoryName: TextView = view.findViewById(R.id.repository_name)
        val authorName: TextView = view.findViewById(R.id.repository_author_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.repositorio_item, parent, false)
        return RepositoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val repository = repositoryList[position]
        holder.authorImage.setImageResource(repository.authorImage)
        holder.repositoryName.text = repository.repositoryName
        holder.authorName.text = repository.authorName
    }

    override fun getItemCount(): Int {
        return repositoryList.size
    }
}
