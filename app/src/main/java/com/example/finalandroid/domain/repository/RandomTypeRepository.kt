package com.example.finalandroid.domain.repository

import com.example.finalandroid.data.models.Movie

interface RandomTypeRepository {

    suspend fun getTvSeries(type: String, page: Int) :List<Movie>

}