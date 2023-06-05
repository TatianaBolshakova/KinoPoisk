package com.example.finalandroid.data.popularspagedmovielist

import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.retrofit
import kotlinx.coroutines.delay

class MoviePagedListRepository {


    suspend fun getPopulars(page: Int):List<Movie>{
        delay(1000)
        return retrofit.populars(page).items
    }


}