package com.example.finalandroid.presentation.search.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.repository.SearchKeyWordRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchKeyWordViewModel private constructor(
    private val repository: SearchKeyWordRepositoryImpl
): ViewModel(){
    constructor():this(SearchKeyWordRepositoryImpl())

    private val _movie = MutableStateFlow<List<Movie>>(emptyList())
    val movie = _movie.asStateFlow()

     fun loadMovie(keyword: String) {
        viewModelScope.launch(Dispatchers.IO){
            kotlin.runCatching {
                repository.getSearchKeyWord(keyword)
            }.fold(
                onSuccess = {_movie.value = it},
                onFailure = { Log.d("MovieListViewModel", it.message ?: "")}
            )
        }
    }
}