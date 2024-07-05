package com.geeklord.imdbapp.Repository

import com.geeklord.imdbapp.Models.Movies
import com.geeklord.imdbapp.Utils.NetworkResult

interface MoviesRepository {

    suspend fun getAllMovies(search : String) : NetworkResult<Movies>
}