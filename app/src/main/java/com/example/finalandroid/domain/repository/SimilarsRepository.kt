package com.example.finalandroid.domain.repository


import com.example.finalandroid.data.models.Movie


interface SimilarsRepository {

    suspend fun getSimilars(id: Int): List<Movie>
}