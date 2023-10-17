package com.example.finalandroid.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class FilmList(
    @Json(name = "total") val total: Int,
    @Json(name = "items") val items: List<InfoMovie>
)