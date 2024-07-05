package com.geeklord.imdbapp.api

import com.geeklord.imdbapp.Models.Movies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("?apikey=6461c46b")
    suspend fun getData(@Query("s") s : String) : Response<Movies>
}