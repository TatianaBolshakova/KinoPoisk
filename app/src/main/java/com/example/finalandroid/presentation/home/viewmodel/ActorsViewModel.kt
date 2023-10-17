package com.example.finalandroid.presentation.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalandroid.data.models.InfoActorsItem
import com.example.finalandroid.data.repository.ActorsRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ActorsViewModel private constructor(
    private val repository: ActorsRepositoryImpl
): ViewModel(){
    constructor():this(ActorsRepositoryImpl())

    private val _actors = MutableStateFlow <List<InfoActorsItem>>(emptyList())
    val actors = _actors.asStateFlow()


    fun loadInfo(filmId:Int) {
        viewModelScope.launch(Dispatchers.IO){
            kotlin.runCatching {
                repository.getActors(filmId)
            }.fold(
                onSuccess = {_actors.value = it},
                onFailure = { Log.d("MovieListViewModel", it.message ?: "")}
            )
        }
    }



}