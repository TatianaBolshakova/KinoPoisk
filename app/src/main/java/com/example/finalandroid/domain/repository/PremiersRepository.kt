package com.example.finalandroid.domain.repository


import com.example.finalandroid.data.models.Movie

interface PremiersRepository{

    suspend fun getMoviePremiers(year: Int, month: String):List<Movie>

}