package com.example.finalandroid.data.repository

import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.api.retrofit
import com.example.finalandroid.domain.repository.RandomTypeRepository


class RandomTypeRepositoryImpl: RandomTypeRepository {

   override suspend fun getTvSeries(type: String, countries: Int) :List<Movie>{
        return retrofit.tvSeries(type, countries).items
    }

}