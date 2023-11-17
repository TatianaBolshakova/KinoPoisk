package com.example.finalandroid.data.repository


import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.api.retrofit
import com.example.finalandroid.domain.repository.SearchRepository

class SearchRepositoryImpl : SearchRepository {
    override suspend fun getSearchKeyWord(
        keyword: String,
        type: String,
        country: Array<Int>,
        genre: Array<Int>,
        year1: Int,
        year2: Int,
        rating1: Int,
        rating2: Int,
        order: String
    ): List<Movie> {
        return retrofit.getSearchKeyWord(
            keyword = keyword,
            countries = country,
           genres = genre,
            order = order,
            type = type,
            ratingFrom = rating1,
            ratingTo = rating2,
            yearFrom = year1,
            yearTo = year2
        ).items
    }
}