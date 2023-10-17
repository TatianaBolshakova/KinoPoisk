package com.example.finalandroid.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ListIdAndCountries (
    @Json(name = "genres") val genres: List<Genre>,
    @Json(name = "countries") val countries: List<Country>
        )