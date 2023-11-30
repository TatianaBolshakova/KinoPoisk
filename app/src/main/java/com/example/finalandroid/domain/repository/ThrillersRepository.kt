package com.example.finalandroid.domain.repository


import com.example.finalandroid.data.models.Movie


interface ThrillersRepository {

    suspend fun getMovieThrillers(page: Int):List<Movie>

}