package com.geeklord.imdbapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load
import coil.transform.RoundedCornersTransformation
import com.geeklord.imdbapp.Models.Search
import com.geeklord.imdbapp.databinding.ActivityMain2Binding
import com.geeklord.imdbapp.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity2 : AppCompatActivity() {

    private var _binding : ActivityMain2Binding?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val movieDataJson = intent.getStringExtra("movieData")
        if (movieDataJson != null) {
            val movieData = Gson().fromJson(movieDataJson, Search::class.java)

            binding.textView.text = movieData.Title
            binding.imageView.load(movieData.Poster){
                transformations(RoundedCornersTransformation(16f)) // Set the radius of rounded corners
                size(200,200)
                placeholder(R.drawable.ic_launcher_background) // Placeholder image while loading
                error(R.drawable.ic_launcher_foreground) // Error image if loading fails
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}