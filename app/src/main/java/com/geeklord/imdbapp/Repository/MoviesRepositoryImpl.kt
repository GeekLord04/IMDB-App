package com.geeklord.imdbapp.Repository

import com.geeklord.imdbapp.Models.Movies
import com.geeklord.imdbapp.Utils.NetworkResult
import com.geeklord.imdbapp.api.ApiService
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(private val apiService: ApiService) : MoviesRepository {
    suspend override fun getAllMovies(search: String): NetworkResult<Movies> {
        val response =  apiService.getData(search)
        try {
            if (response.isSuccessful && response.body() != null){
                return NetworkResult.Success(response.body()!!)
            }
            else if(response.errorBody() != null){
                return NetworkResult.Error(response.message())
            }
            else{
                return NetworkResult.Error("Something went wrong")
            }
        }
        catch (e : Exception){
            return NetworkResult.Error(e.message)
        }
    }
}