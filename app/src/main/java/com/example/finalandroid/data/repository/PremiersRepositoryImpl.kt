package com.example.finalandroid.data.repository


import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.api.retrofit
import com.example.finalandroid.domain.repository.PremiersRepository

class PremiersRepositoryImpl: PremiersRepository {

   override suspend fun getMoviePremiers(year: Int, month: String):List<Movie>{
        return retrofit.premieres(year, month).items
    }

}