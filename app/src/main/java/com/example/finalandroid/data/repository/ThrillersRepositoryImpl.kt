package com.example.finalandroid.data.repository


import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.api.retrofit
import com.example.finalandroid.domain.repository.ThrillersRepository

class ThrillersRepositoryImpl: ThrillersRepository {

   override suspend fun getMovieThrillers(page: Int):List<Movie>{
        return retrofit.thrillers(page).items
    }
}