package com.example.finalandroid.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.repository.SimilarsRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SimilarsViewModel private constructor(
    private val repository: SimilarsRepositoryImpl
): ViewModel(){
    constructor():this(SimilarsRepositoryImpl())

    private val _movie = MutableStateFlow<List<Movie>?>(emptyList())
    val movie = _movie.asStateFlow()


     fun loadInfo(id: Int) {
        viewModelScope.launch(Dispatchers.IO){
            kotlin.runCatching {
                repository.getSimilars(id)
            }.fold(
                onSuccess = {_movie.value = it },
                onFailure = { Log.d("MovieListViewModel", it.message ?: "")}
            )
        }
    }
}