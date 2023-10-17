package com.example.finalandroid.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InfoActors (
    @Json(name = "items") val items: List<InfoActorsItem>
        )