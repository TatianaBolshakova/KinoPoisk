package com.example.finalandroid.data.models




data class Images(
    val items: List<Items>,
    val total: Int,
    val totalPages: Int
)
data class Items(
    val imageUrl: String,
    val previewUrl: String,

)