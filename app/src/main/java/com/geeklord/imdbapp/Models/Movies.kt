package com.geeklord.imdbapp.Models

data class Movies(
    val Response: String,
    val Search: List<Search>,
    val totalResults: String
)