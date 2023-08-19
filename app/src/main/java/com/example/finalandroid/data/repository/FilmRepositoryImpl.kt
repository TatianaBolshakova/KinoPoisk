package com.example.finalandroid.data.repository


import com.example.finalandroid.data.models.InfoMovie
import com.example.finalandroid.data.api.retrofit
import com.example.finalandroid.domain.repository.FilmRepository

class FilmRepositoryImpl: FilmRepository {

   override suspend fun getInfoFilm(id: Int): InfoMovie {
        return retrofit.film(id)
    }

}