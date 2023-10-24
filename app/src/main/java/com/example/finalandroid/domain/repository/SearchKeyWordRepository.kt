package com.example.finalandroid.domain.repository


import com.example.finalandroid.data.models.Movie

interface SearchKeyWordRepository{

    suspend fun getSearchKeyWord(keyword: String):List<Movie>

}