package com.example.finalandroid.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.repository.TopMovieRepositoryImpl


private const val TOP_250_EN = "TOP_250_BEST_FILMS"

class MovieTopPagingSource : PagingSource<Int, Movie>() {
    private val repository = TopMovieRepositoryImpl()

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: FIRST_PAGE
       return kotlin.runCatching {
            repository.getTopMovie(TOP_250_EN, page)
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