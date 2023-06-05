package com.example.finalandroid.data.popularspagedmovielist
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.retrofit


class MoviePagedRepository() {

    suspend fun getMovie(page: Int):List<Movie>{
        return retrofit.populars(page).items
    }
}