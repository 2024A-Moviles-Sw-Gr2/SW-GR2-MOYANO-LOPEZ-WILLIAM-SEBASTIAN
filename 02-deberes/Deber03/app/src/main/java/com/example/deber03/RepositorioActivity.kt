package com.example.deber03

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RepositorioActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repositorio)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView_repositories)

        // Sample data
        val repositories = listOf(
            Repositorio("WeatherApp", "John Doe", R.drawable.perfil_github),
            Repositorio("ChatBotAI", "Alice Smith", R.drawable.perfil_github),
            Repositorio("ExpenseTracker", "Robert Brown", R.drawable.perfil_github),
            Repositorio("FitnessPal", "Emily Davis", R.drawable.perfil_github),
            Repositorio("ECommercePlatform", "Michael Johnson", R.drawable.perfil_github),
            Repositorio("RecipeFinder", "Jessica Miller", R.drawable.perfil_github),
            Repositorio("CryptoDashboard", "Daniel Wilson", R.drawable.perfil_github),
            Repositorio("LanguageTranslator", "Sophia Taylor", R.drawable.perfil_github),
            Repositorio("MusicPlayer", "James Anderson", R.drawable.perfil_github),
            Repositorio("TaskManager", "Olivia Martinez", R.drawable.perfil_github),
            Repositorio("TravelGuide", "David Garcia", R.drawable.perfil_github),
            Repositorio("NewsAggregator", "Isabella Thomas", R.drawable.perfil_github),
            Repositorio("StockTracker", "Matthew White", R.drawable.perfil_github),
            Repositorio("VirtualClassroom", "Mia Harris", R.drawable.perfil_github),
            Repositorio("AIImageProcessor", "Ethan Clark", R.drawable.perfil_github),
            Repositorio("BudgetPlanner", "Charlotte Lewis", R.drawable.perfil_github),
            Repositorio("RealEstateApp", "Aiden Walker", R.drawable.perfil_github),
            Repositorio("RecipeBook", "Amelia Robinson", R.drawable.perfil_github),
            Repositorio("EventScheduler", "Lucas Hall", R.drawable.perfil_github),
            Repositorio("HealthTracker", "Harper Young", R.drawable.perfil_github),
            Repositorio("LearningPlatform", "Elijah Scott", R.drawable.perfil_github)
        )


        val adapter = RepositorioRecyclerView(repositories)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}
