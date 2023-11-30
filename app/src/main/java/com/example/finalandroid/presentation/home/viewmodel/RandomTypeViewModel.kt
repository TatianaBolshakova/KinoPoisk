package com.example.finalandroid.presentation.home.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.pagingsource.MovieRandomTypePagingSource
import kotlinx.coroutines.flow.Flow

class RandomTypeViewModel private constructor(
) : ViewModel() {

    val pagedMovies: Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { MovieRandomTypePagingSource() }
    ).flow.cachedIn(viewModelScope)

}