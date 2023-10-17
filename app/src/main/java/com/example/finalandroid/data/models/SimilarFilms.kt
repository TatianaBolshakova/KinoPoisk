package com.example.finalandroid.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class SimilarFilms(
    @Json(name = "items") val items: List<Movie>,
    @Json(name = "total") val total: Int
)
