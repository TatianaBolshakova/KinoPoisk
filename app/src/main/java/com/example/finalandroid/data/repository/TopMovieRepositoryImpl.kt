package com.example.finalandroid.data.repository

import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.api.retrofit
import com.example.finalandroid.domain.repository.TopMovieRepository

class TopMovieRepositoryImpl: TopMovieRepository {

    override suspend fun getTopMovie(type: String, page: Int):List<Movie>{
        return retrofit.topList(type, page).films
    }

}