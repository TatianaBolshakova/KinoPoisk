package com.example.finalandroid.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    @Json(name = "kinopoiskId") val kinopoiskId: Int,
    @Json(name = "nameRu") val nameRu: String,
    @Json(name = "posterUrl") val posterUrl: String,
    @Json(name = "posterUrlPreview") val posterUrlPreview: String,
    @Json(name = "genres") val genres: List <Genre>,
    @Json(name = "premiereRu") val premiereRu: String,
    @Json(name = "countries") val countries: List <Country>,
    @Json(name = "rating") val rating: Double,
    @Json(name = "ratingKinopoisk") val ratingKinopoisk: Double,
    @Json(name = "ratingImdb") val ratingImdb: Double,
    @Json(name = "filmId") val filmId: Int,
    @Json(name = "imageUrl") val imageUrl: String,
    @Json(name = "type") val type: String
)

@JsonClass(generateAdapter = true)
data class Genre(
    @Json(name = "genre") val genre: String,
    @Json(name = "id") val id: Int
)

@JsonClass(generateAdapter = true)
data class Country(
    @Json(name = "country") val country: String,
    @Json(name = "id") val id: Int
)