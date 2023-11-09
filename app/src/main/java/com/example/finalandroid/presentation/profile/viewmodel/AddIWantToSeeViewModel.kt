package com.example.finalandroid.presentation.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalandroid.data.db.IWantToSeeDao
import com.example.finalandroid.data.db.entity.IWantToSeeFilms
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddIWantToSeeViewModel constructor(
    private val dao: IWantToSeeDao

) : ViewModel() {

    var allSelectedFilm: StateFlow<List<IWantToSeeFilms>> = this.dao.getAll()
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
                IWantToSeeFilms(
                    iWantToSeeFilmId = id,
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
                IWantToSeeFilms(
                    iWantToSeeFilmId = id,
                    nameFilm = nameFilm,
                    urlFilm = urlFilm,
                    genre = genre,
                )
            )

        }
    }
}