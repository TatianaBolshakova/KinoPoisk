package com.example.finalandroid.domain.repository

import com.example.finalandroid.data.models.Country
import com.example.finalandroid.data.models.Genre
import com.example.finalandroid.data.models.Movie


interface ThrillersRepository {

    suspend fun getMovieThrillers():List<Movie>
    suspend fun getId(): List<Genre>
    suspend fun getCountries(): List<Country>
}