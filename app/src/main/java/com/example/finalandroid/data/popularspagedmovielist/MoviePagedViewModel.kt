package com.example.finalandroid.data.popularspagedmovielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.finalandroid.data.models.Movie
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MoviePagedViewModel private constructor(
    private val repository: MoviePagedRepository
): ViewModel(){
    constructor():this(MoviePagedRepository())

    private val _movie = MutableStateFlow<List<Movie>>(emptyList())
    val movie = _movie.asStateFlow()

    val pagedMovie: Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 20),
        initialKey = null,
        pagingSourceFactory = { MoviePagingSource() }
    ).flow.cachedIn(viewModelScope)


}