package com.example.finalandroid.presentation.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalandroid.data.db.LikeDao
import com.example.finalandroid.data.db.entity.LikeFilms
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddLikeFilmViewModel constructor(
    private val dao: LikeDao

) : ViewModel() {

    var allSelectedFilm: StateFlow<List<LikeFilms>> = this.dao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun addFilm(
        id: Int,
        nameFilm: String,
        urlFilm: String,
        genre: String ,

        ) {
        viewModelScope.launch {
            dao.insert(
                LikeFilms(
                    likeFilmId = id,
                    nameFilm = nameFilm,
                    urlFilm = urlFilm,
                    genre = genre,
                )
            )

        }
    }
    fun deleteFilm(
        id: Int,
        nameFilm: String,
        urlFilm: String,
        genre: String ,

        ) {
        viewModelScope.launch {
            dao.delete(
                LikeFilms(
                    likeFilmId = id,
                    nameFilm = nameFilm,
                    urlFilm = urlFilm,
                    genre = genre,
                )
            )

        }
    }


}