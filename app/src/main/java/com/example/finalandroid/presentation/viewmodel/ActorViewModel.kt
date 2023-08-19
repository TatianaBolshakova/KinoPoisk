package com.example.finalandroid.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalandroid.data.models.InfoActorItem
import com.example.finalandroid.data.repository.ActorRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ActorViewModel private constructor(
    private val repository: ActorRepositoryImpl
): ViewModel(){
    constructor():this(ActorRepositoryImpl())

    private val _actor = MutableStateFlow <InfoActorItem?>(null)
    val actor = _actor.asStateFlow()


    fun loadInfo(filmId:Int) {
        viewModelScope.launch(Dispatchers.IO){
            kotlin.runCatching {
                repository.getActors(filmId)
            }.fold(
                onSuccess = {_actor.value = it},
                onFailure = { Log.d("MovieListViewModel", it.message ?: "")}
            )
        }
    }



}