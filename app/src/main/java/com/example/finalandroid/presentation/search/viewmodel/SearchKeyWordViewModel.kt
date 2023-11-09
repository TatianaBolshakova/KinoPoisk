package com.example.finalandroid.presentation.search.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.repository.SearchKeyWordRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SearchKeyWordViewModel private constructor(
    private val repository: SearchKeyWordRepositoryImpl
) : ViewModel() {
    constructor() : this(SearchKeyWordRepositoryImpl())

    private val _movie = MutableStateFlow<List<Movie>>(emptyList())
    val movie = _movie.asStateFlow()

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    

    fun loadMovie(keyword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                repository.getSearchKeyWord(keyword)
            }.fold(
                onSuccess = { _movie.value = it },
                onFailure = { _error.send("К сожалению, по Вашему запросу ничего не найдено") }
            )

        }
    }
}
