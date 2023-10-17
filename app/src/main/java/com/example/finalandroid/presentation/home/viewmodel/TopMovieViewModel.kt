package com.example.finalandroid.presentation.home.viewmodel

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

private const val TOP_250_EN = "TOP_250_BEST_FILMS"
private const val TOP_100_EN = "TOP_100_POPULAR_FILMS"
private const val TOP_AWAIT_EN = "TOP_AWAIT_FILMS"
private const val TOP_250_RU = "ТОП 250 ЛУЧШИХ ФИЛЬМОВ"
private const val TOP_100_RU = "ТОП 100 ПОПУЛЯРНЫХ ФИЛЬМОВ"
private const val TOP_AWAIT_RU = "ТОП: ОЖИДАЕМЫЕ ФИЛЬМЫ"

class TopMovieViewModel private constructor(
    private val repository: TopMovieRepositoryImpl
) : ViewModel() {
    constructor() : this(TopMovieRepositoryImpl())

    private val _movie = MutableStateFlow<List<Movie>>(emptyList())
    val movie = _movie.asStateFlow()


    val type = when (Random.nextInt(1..3)) {
        1 -> TOP_250_EN
        2 -> TOP_100_EN
        else -> TOP_AWAIT_EN
    }
    val nameRandomTop = when (type) {
        TOP_250_EN -> TOP_250_RU
        TOP_100_EN -> TOP_100_RU
        else -> TOP_AWAIT_RU
    }


        init {
        loadMovie()
    }
    fun loadMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {

                repository.getTopMovie(type)
            }.fold(
                onSuccess = { _movie.value = it },
                onFailure = { Log.d("MovieListViewModel", it.message ?: "") }
            )
        }
    }
}