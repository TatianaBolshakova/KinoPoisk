package com.example.finalandroid.presentation.search.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.repository.SearchRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SearchViewModel private constructor(
    private val repository: SearchRepositoryImpl
) : ViewModel() {
    constructor() : this(SearchRepositoryImpl())

    private val _movie = MutableStateFlow<List<Movie>>(emptyList())
    val movie = _movie.asStateFlow()

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()


    fun loadMovie(
        keyword: String = "",
        type: String,
        country: Array<Int>,
        genre: Array<Int>,
        year1: Int,
        year2: Int,
        rating1: Int,
        rating2: Int,
        order: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                repository.getSearchKeyWord(
                    keyword, type, country, genre, year1, year2, rating1, rating2, order
                )
            }.fold(
                onSuccess = { _movie.value = it },
                onFailure = { Log.d("SearchViewModel", it.message ?: "")}
            )

        }
    }
}
