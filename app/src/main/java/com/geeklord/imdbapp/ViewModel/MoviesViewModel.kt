package com.geeklord.imdbapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geeklord.imdbapp.Models.Movies
import com.geeklord.imdbapp.Repository.MoviesRepositoryImpl
import com.geeklord.imdbapp.Utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository : MoviesRepositoryImpl) : ViewModel() {

    private val _movies = MutableLiveData<NetworkResult<Movies>>()
    val moviesList : LiveData<NetworkResult<Movies>> get() = _movies

    suspend fun getAllMovies(search : String){
        _movies.postValue(NetworkResult.Loading())
        val response = withContext(Dispatchers.IO){
            repository.getAllMovies(search)
        }
        _movies.postValue(response)
    }
}