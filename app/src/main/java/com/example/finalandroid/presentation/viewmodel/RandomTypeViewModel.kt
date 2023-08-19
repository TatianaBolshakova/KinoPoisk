package com.example.finalandroid.presentation.viewmodel

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

class RandomTypeViewModel private constructor(
    private val repository: RandomTypeRepositoryImpl
): ViewModel(){
    constructor():this(RandomTypeRepositoryImpl())

    private val _movie = MutableStateFlow<List<Movie>>(emptyList())
    val movie = _movie.asStateFlow()

    init {
        loadMovie()
    }
     var type = when(Random.nextInt(1 until 5)){
        1->"FILM"
        2->"TV_SHOW"
        3->"TV_SERIES"
        4->"MINI_SERIES"
        else->"ALL"
    }

    private fun loadMovie() {
        viewModelScope.launch(Dispatchers.IO){
            kotlin.runCatching {
                repository.getTvSeries(type, Random.nextInt(1 ..3))
            }.fold(
                onSuccess = {_movie.value = it},
                onFailure = { Log.d("MovieListViewModel", it.message ?: "")}
            )
        }
    }
}