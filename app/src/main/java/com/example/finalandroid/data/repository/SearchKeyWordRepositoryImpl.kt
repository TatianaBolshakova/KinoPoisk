package com.example.finalandroid.data.repository


import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.api.retrofit
import com.example.finalandroid.domain.repository.SearchKeyWordRepository

class SearchKeyWordRepositoryImpl : SearchKeyWordRepository {
    override suspend fun getSearchKeyWord(keyword: String): List<Movie> {
        return retrofit.getSearchKeyWord(keyword).items
    }
}