package com.example.finalandroid.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.repository.TopMovieRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.random.nextInt

class TopMovieViewModel private constructor(
    private val repository: TopMovieRepositoryImpl
): ViewModel(){
    constructor():this(TopMovieRepositoryImpl())

    private val _movie = MutableStateFlow<List<Movie>>(emptyList())
    val movie = _movie.asStateFlow()

    init {
        loadMovie()
    }
    var type = when(Random.nextInt(1 until 3)){
        1->"TOP_250_BEST_FILMS"
        2->"TOP_100_POPULAR_FILMS"
        else->"TOP_AWAIT_FILMS"
    }
    private fun loadMovie() {
        viewModelScope.launch(Dispatchers.IO){
            kotlin.runCatching {
                repository.getTopMovie(type)
            }.fold(
                onSuccess = {_movie.value = it},
                onFailure = { Log.d("MovieListViewModel", it.message ?: "")}
            )
        }
    }
}