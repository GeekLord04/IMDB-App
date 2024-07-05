package com.geeklord.imdbapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.geeklord.imdbapp.Models.Search
import com.geeklord.imdbapp.Utils.Constant.TAG
import com.geeklord.imdbapp.Utils.NetworkResult
import com.geeklord.imdbapp.ViewModel.MoviesViewModel
import com.geeklord.imdbapp.databinding.ActivityMainBinding
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding ?= null
    private val binding get() = _binding!!

    private val moviesViewModel by viewModels<MoviesViewModel>()

    private lateinit var adapter : ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bindObserver()


        binding.button.setOnClickListener {
            if (binding.editTextText.text.isNotEmpty() && binding.editTextText.text.toString().length > 2){
                lifecycleScope.launch {
                    moviesViewModel.getAllMovies(binding.editTextText.text.toString())
                }
            }
            else if (binding.editTextText.text.toString().length > 2){
                Toast.makeText(this, "Should be greater than 2", Toast.LENGTH_SHORT).show()
            }
        }

        adapter = ItemAdapter(::onItemCLicked)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter


    }

    private fun bindObserver() {
        moviesViewModel.moviesList.observe(this){
            when(it){
                is NetworkResult.Success -> {
                    Log.d(TAG, "Success : ${it.data} ")
                    adapter.submitList(it.data?.Search)

                }
                is NetworkResult.Error -> {
                    Log.d(TAG, "Error: ${it.message}")
                }
                is NetworkResult.Loading -> {
                    Log.d(TAG, "Loading..")
                }

                else -> {}
            }
        }
    }

    private fun onItemCLicked(movieData : Search){
        val intent = Intent(this, MainActivity2::class.java)
        intent.putExtra("movieData", Gson().toJson(movieData))
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}