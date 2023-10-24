package com.example.finalandroid.presentation.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalandroid.data.db.SelectedFilmsDao
import com.example.finalandroid.data.db.entity.SelectedFilms
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddFilmViewModel2 constructor(
    private val dao: SelectedFilmsDao

) : ViewModel() {

    var allSelectedFilm: StateFlow<List<SelectedFilms>> = this.dao.getAllSelectedFilms2()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun addFilm(
        id: Long,
        nameCollection: String,
        nameFilm: String,
        urlFilm: String,
        isIWantToSee:Boolean
    ) {
        viewModelScope.launch {
            dao.insertSelectedFilm(
                SelectedFilms(
                    selectedFilmsId = id,
                    nameCollection = nameCollection,
                    nameFilm = nameFilm,
                    urlFilm = urlFilm,
                    isIWantToSee = isIWantToSee
                )
            )

        }
    }
    fun deleteFilm(
        id: Long,
        nameCollection: String,
        nameFilm: String,
        urlFilm: String,
        isIWantToSee:Boolean
    ) {
        viewModelScope.launch {
            dao.deleteSelectedFilm(
                SelectedFilms(
                    selectedFilmsId = id,
                    nameCollection = nameCollection,
                    nameFilm = nameFilm,
                    urlFilm = urlFilm,
                    isIWantToSee = isIWantToSee
                )
            )

        }
    }
}