package com.example.finalandroid.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InfoActorItem(
    @Json(name = "age") val age: Int,
    @Json(name = "birthday") val birthday: String,
    @Json(name = "birthplace") val birthplace: String,
    @Json(name = "death") val death: String,
    @Json(name = "deathplace") val deathplace: String,
    @Json(name = "facts") val facts: List<String>,
    @Json(name = "films") val films: List<Movie>,
    @Json(name = "growth") val growth: String,
    @Json(name = "hasAwards") val hasAwards: Int,
    @Json(name = "nameEn") val nameEn: String,
    @Json(name = "nameRu") val nameRu: String,
    @Json(name = "personId") val personId: Int,
    @Json(name = "posterUrl") val posterUrl: String,
    @Json(name = "profession") val profession: String,
    @Json(name = "sex") val sex: String,
    @Json(name = "spouses") val spouses: List<Any>,
    @Json(name = "webUrl") val webUrl: String
)