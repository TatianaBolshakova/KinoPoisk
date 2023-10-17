package com.example.finalandroid.presentation.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalandroid.data.models.Items
import com.example.finalandroid.data.repository.ImagesRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ImagesViewModel private constructor(
    private val repository: ImagesRepositoryImpl
): ViewModel(){
    constructor():this(ImagesRepositoryImpl())

    private val _images = MutableStateFlow <List<Items>>(emptyList())
    val images = _images.asStateFlow()


    fun loadInfo(id:Int) {
        viewModelScope.launch(Dispatchers.IO){
            kotlin.runCatching {
                repository.getImages(id)
            }.fold(
                onSuccess = { _images.value = it },
                onFailure = { Log.d("MovieListViewModel", it.message ?: "")}
            )
        }
    }
}