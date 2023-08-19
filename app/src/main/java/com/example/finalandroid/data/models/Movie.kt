package com.example.finalandroid.data.models

data class Movie(
    val kinopoiskId: Int,
    val nameRu: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val genres: List <Genre>,
    val premiereRu: String,
    val countries: List <Country>,
    val rating: Double,
    val filmId: Int,
    val imageUrl: String,
    val type: String
)

data class Genre(
    val genre: String
)

data class Country(
    val country: String
)