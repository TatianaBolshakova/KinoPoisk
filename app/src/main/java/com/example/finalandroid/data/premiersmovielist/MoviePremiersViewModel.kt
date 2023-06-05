package com.example.finalandroid.data.premiersmovielist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalandroid.data.models.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MoviePremiersViewModel private constructor(
    private val repository: MovieRepository
): ViewModel(){
    constructor():this(MovieRepository())

    private val _movie = MutableStateFlow<List<Movie>>(emptyList())
    val movie = _movie.asStateFlow()

    init {
        loadMovie()
    }

    private fun loadMovie() {
        viewModelScope.launch(Dispatchers.IO){
            kotlin.runCatching {
                repository.getMoviePremiers(2023,"JUNE")
            }.fold(
                onSuccess = {_movie.value = it},
                onFailure = { Log.d("MovieListViewModel", it.message ?: "")}
            )
        }
    }
}