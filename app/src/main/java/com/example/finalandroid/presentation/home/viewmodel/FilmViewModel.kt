package com.example.finalandroid.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalandroid.data.models.InfoMovie
import com.example.finalandroid.data.repository.FilmRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FilmViewModel private constructor(

    private val repository: FilmRepositoryImpl,

) : ViewModel() {
    constructor():this(FilmRepositoryImpl())


    private val _info = MutableStateFlow<InfoMovie?>(null)
    val info = _info.asStateFlow()

    fun loadInfo(id: Int) {
        viewModelScope.launch {
            _info.value = repository.getInfoFilm(id)
        }
    }

}