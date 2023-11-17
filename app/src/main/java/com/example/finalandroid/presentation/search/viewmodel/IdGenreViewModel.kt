package com.example.finalandroid.presentation.search.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalandroid.data.models.Genre
import com.example.finalandroid.data.repository.IdGenreRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class IdGenreViewModel private constructor(
    private val repository: IdGenreRepositoryImpl
) : ViewModel() {
    constructor() : this(IdGenreRepositoryImpl())

    private val _idGenre = MutableStateFlow<Array<Genre>>(emptyArray())
    val idGenre = _idGenre.asStateFlow()


    fun loadIdGenre() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                repository.idAndGenre()
            }.fold(
                onSuccess = { _idGenre.value = it },
                onFailure = { Log.d("IdCountryViewModel", it.message ?: "")}
            )

        }
    }

}
