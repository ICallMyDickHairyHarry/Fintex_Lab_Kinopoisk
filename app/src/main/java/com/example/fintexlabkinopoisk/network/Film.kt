package com.example.fintexlabkinopoisk.network

data class Film(
    val filmId: Int,
    val nameRu: String,
    val year: String,
    val genres: List<Map<String,String>>,
    val posterUrlPreview: String
)