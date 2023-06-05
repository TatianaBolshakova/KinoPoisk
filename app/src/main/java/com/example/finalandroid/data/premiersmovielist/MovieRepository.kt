package com.example.finalandroid.data.premiersmovielist

import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.retrofit

class MovieRepository() {

    suspend fun getMoviePremiers(year: Int, month: String):List<Movie>{
        return retrofit.premieres(year, month).items
    }

}