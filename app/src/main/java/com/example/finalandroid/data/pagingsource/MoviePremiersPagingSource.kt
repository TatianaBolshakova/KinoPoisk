package com.example.finalandroid.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.repository.PremiersRepositoryImpl

class MoviePremiersPagingSource : PagingSource<Int, Movie>() {
    private val repository = PremiersRepositoryImpl()
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: FIRST_PAGE
       return kotlin.runCatching {
            repository.getMoviePremiers(2023, " OCTOBER", page)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data =  it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page +1
                )
            }, onFailure = {LoadResult.Error(it)}
        )
    }
    private companion object {
        private const val FIRST_PAGE = 1
    }
}