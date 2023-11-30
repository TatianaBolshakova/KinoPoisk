package com.example.finalandroid.presentation.home.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.pagingsource.MovieTopPagingSource
import kotlinx.coroutines.flow.Flow

class TopMovieViewModel private constructor(

) : ViewModel() {
    val pagedMovies: Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { MovieTopPagingSource() }
    ).flow.cachedIn(viewModelScope)
}