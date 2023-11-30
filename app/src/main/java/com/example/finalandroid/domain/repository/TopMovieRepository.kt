package com.example.finalandroid.domain.repository

import com.example.finalandroid.data.models.Movie


interface TopMovieRepository {

    suspend fun getTopMovie(type: String, page: Int):List<Movie>
}