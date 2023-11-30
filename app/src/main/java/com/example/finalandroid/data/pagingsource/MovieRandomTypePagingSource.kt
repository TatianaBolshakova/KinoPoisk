package com.example.finalandroid.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.repository.RandomTypeRepositoryImpl
import kotlin.random.Random
import kotlin.random.nextInt
private const val FILM_EN = "FILM"
private const val TV_SHOW_EN = "TV_SHOW"
private const val TV_SERIES_EN = "TV_SERIES"
private const val MINI_SERIES_EN = "MINI_SERIES"
private const val ALL_EN = "ALL"

class MovieRandomTypePagingSource : PagingSource<Int, Movie>() {
    private val repository = RandomTypeRepositoryImpl()

    var type = when (Random.nextInt(1 until 5)) {
        1 -> FILM_EN
        2 -> TV_SHOW_EN
        3 -> TV_SERIES_EN
        4 -> MINI_SERIES_EN
        else -> ALL_EN
    }
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: FIRST_PAGE


       return kotlin.runCatching {
            repository.getTvSeries(type,page)
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