package com.example.finalandroid.presentation.home.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.pagingsource.MovieThrillersPagingSource
import kotlinx.coroutines.flow.Flow


class ThrillersViewModel private constructor(

): ViewModel(){
    val pagedMovies: Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { MovieThrillersPagingSource() }
    ).flow.cachedIn(viewModelScope)
}