package com.example.finalandroid.data.repository

import com.example.finalandroid.data.models.Country
import com.example.finalandroid.data.models.Genre
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.api.retrofit
import com.example.finalandroid.domain.repository.ThrillersRepository

class ThrillersRepositoryImpl: ThrillersRepository {

   override suspend fun getMovieThrillers():List<Movie>{
        return retrofit.thrillers().items
    }
   override suspend fun getId(): List<Genre> {
        return retrofit.idAndCountries().genres
    }
   override suspend fun getCountries(): List<Country> {
        return retrofit.idAndCountries().countries
    }
}