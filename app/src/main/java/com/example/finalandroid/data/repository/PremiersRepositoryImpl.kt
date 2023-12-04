package com.example.finalandroid.data.repository


import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.api.retrofit
import com.example.finalandroid.domain.repository.PremiersRepository
import kotlinx.coroutines.delay

class PremiersRepositoryImpl: PremiersRepository {

   override suspend fun getMoviePremiers(year: Int, month: String, page: Int):List<Movie>{

       return retrofit.premieres(year, month, page).items
    }

}