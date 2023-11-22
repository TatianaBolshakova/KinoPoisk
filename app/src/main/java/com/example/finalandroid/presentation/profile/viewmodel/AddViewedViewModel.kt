package com.example.finalandroid.presentation.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalandroid.data.db.ViewedDao
import com.example.finalandroid.data.db.entity.ViewedFilms
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddViewedViewModel constructor(
    private val dao: ViewedDao

) : ViewModel() {

    var allViewedFilms: StateFlow<List<ViewedFilms>> = this.dao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )


    fun addViewed(viewedFilmId: Int, nameFilm: String, urlFilm: String, genres:String) {
        viewModelScope.launch {
            dao.insert(
                ViewedFilms(
                    viewedFilmId = viewedFilmId,
                    nameFilm = nameFilm,
                    urlFilm = urlFilm,
                    genres = genres

                )
            )
        }
    }
}