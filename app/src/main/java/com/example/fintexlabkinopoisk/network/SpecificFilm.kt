package com.example.fintexlabkinopoisk.network

data class SpecificFilm (
    val nameRu: String,
    val description: String,
    val year: String,
    val genres: List<Map<String,String>>,
    val countries: List<Map<String,String>>,
    val posterUrl: String
)
