package com.example.finalandroid.presentation.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.repository.PremiersRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PremiersViewModel private constructor(
    private val repository: PremiersRepositoryImpl
): ViewModel(){
    constructor():this(PremiersRepositoryImpl())

    private val _movie = MutableStateFlow<List<Movie>>(emptyList())
    val movie = _movie.asStateFlow()

    init {
        loadMovie()
    }

    private fun loadMovie() {
        viewModelScope.launch(Dispatchers.IO){
            kotlin.runCatching {
                repository.getMoviePremiers(2023," AUGUST")
            }.fold(
                onSuccess = {_movie.value = it},
                onFailure = { Log.d("MovieListViewModel", it.message ?: "")}
            )
        }
    }
}