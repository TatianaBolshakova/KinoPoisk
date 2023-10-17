package com.example.finalandroid.presentation.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.repository.RandomTypeRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.random.nextInt

private const val FILM_EN = "FILM"
private const val TV_SHOW_EN = "TV_SHOW"
private const val TV_SERIES_EN = "TV_SERIES"
private const val MINI_SERIES_EN = "MINI_SERIES"
private const val ALL_EN = "ALL"
private const val FILM_RU = "Фильмы"
private const val TV_SHOW_RU = "Тв-шоу"
private const val TV_SERIES_RU = "Сериалы"
private const val MINI_SERIES_RU = "Мини-сериалы"
private const val ALL_RU = "Все"


class RandomTypeViewModel private constructor(
    private val repository: RandomTypeRepositoryImpl
) : ViewModel() {
    constructor() : this(RandomTypeRepositoryImpl())

    private val _movie = MutableStateFlow<List<Movie>>(emptyList())
    val movie = _movie.asStateFlow()

    init {
        loadMovie()
    }

    var type = when (Random.nextInt(1 until 5)) {
        1 -> FILM_EN
        2 -> TV_SHOW_EN
        3 -> TV_SERIES_EN
        4 -> MINI_SERIES_EN
        else -> ALL_EN
    }
    val nameRandomType = when (type) {
        FILM_EN -> FILM_RU
        TV_SHOW_EN -> TV_SHOW_RU
        TV_SERIES_EN -> TV_SERIES_RU
        MINI_SERIES_EN -> MINI_SERIES_RU
        else -> ALL_RU
    }

    private fun loadMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                repository.getTvSeries(type, Random.nextInt(1..3))
            }.fold(
                onSuccess = { _movie.value = it },
                onFailure = { Log.d("MovieListViewModel", it.message ?: "") }
            )
        }
    }
}