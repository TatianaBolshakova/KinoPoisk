package com.example.finalandroid.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Images(
    @Json(name = "items") val items: List<Items>,
    @Json(name = "total")val total: Int,
    @Json(name = "totalPages")val totalPages: Int
)
@JsonClass(generateAdapter = true)
data class Items(
    @Json(name = "imageUrl")val imageUrl: String,
    @Json(name = "previewUrl")val previewUrl: String,

)