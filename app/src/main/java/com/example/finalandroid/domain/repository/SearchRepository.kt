package com.example.finalandroid.domain.repository


import com.example.finalandroid.data.models.Movie

interface SearchRepository{

    suspend fun getSearchKeyWord(
        keyword: String,
       type: String,
        country: Array<Int>,
        genre: Array<Int>,
        year1: Int,
        year2: Int,
        rating1: Int,
        rating2: Int,
        order: String
        ):List<Movie>
}
