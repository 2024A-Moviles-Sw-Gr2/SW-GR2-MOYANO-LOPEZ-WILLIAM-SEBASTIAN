package com.example.deber03

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OrganizacionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_organizacion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Crear una lista de organizaciones
        val organizacionList = listOf(
            Organizacion("Google", "Sundar Pichai", R.drawable.google_logo),
            Organizacion("Facebook", "Mark Zuckerberg", R.drawable.facebook_logo),
            Organizacion("Apple", "Tim Cook", R.drawable.apple_logo),
            Organizacion("Microsoft", "Satya Nadella", R.drawable.microsoft_logo),
            Organizacion("Amazon", "Jeff Bezos", R.drawable.amazon_logo),
            Organizacion("Netflix", "Reed Hastings", R.drawable.netflix_logo),
            Organizacion("Twitter", "Elon Musk", R.drawable.twitter_logo),
            Organizacion("Spotify", "Daniel Ek", R.drawable.spotify_logo),
            Organizacion("Adobe", "Shantanu Narayen", R.drawable.adobe_logo),
            Organizacion("Tesla", "Elon Musk", R.drawable.tesla_logo),
            Organizacion("IBM", "Arvind Krishna", R.drawable.ibm_logo),
            Organizacion("Intel", "Pat Gelsinger", R.drawable.intel_logo),
            Organizacion("Oracle", "Safra Catz", R.drawable.oracle_logo),
            Organizacion("Salesforce", "Marc Benioff", R.drawable.salesforce_logo),
            Organizacion("Uber", "Dara Khosrowshahi", R.drawable.uber_logo),
            Organizacion("LinkedIn", "Ryan Roslansky", R.drawable.linkedin_logo),
            Organizacion("Snapchat", "Evan Spiegel", R.drawable.snapchat_logo),
            Organizacion("Reddit", "Steve Huffman", R.drawable.reddit_logo),
            Organizacion("WhatsApp", "Will Cathcart", R.drawable.whatsapp_logo),
            Organizacion("Zoom", "Eric Yuan", R.drawable.zoom_logo)
        )

        // Configurar el RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_organizaciones)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = OrganizacionRecyclerView(organizacionList)
    }
}
