package com.example.finalandroid.data.repository


import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.api.retrofit
import com.example.finalandroid.domain.repository.SimilarsRepository

class SimilarsRepositoryImpl: SimilarsRepository {

     override suspend fun getSimilars(id: Int): List<Movie> {
        return retrofit.similar(id).items
    }

}