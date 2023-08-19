package com.example.finalandroid.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.repository.ActorFilmBestRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ActorFilmBestViewModel private constructor(
    private val repository: ActorFilmBestRepositoryImpl
): ViewModel(){
    constructor():this(ActorFilmBestRepositoryImpl())

    private val _actor = MutableStateFlow <List<Movie>> (emptyList())
    val actor = _actor.asStateFlow()


    fun loadInfo(id: Int) {
        viewModelScope.launch(Dispatchers.IO){
            kotlin.runCatching {
                repository.getActorFilm(id)
            }.fold(
                onSuccess = {_actor.value = it },
                onFailure = { Log.d("MovieListViewModel", it.message ?: "")}
            )
        }
    }



}