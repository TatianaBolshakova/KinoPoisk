package com.example.finalandroid.presentation.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalandroid.data.db.WereWonderingDao
import com.example.finalandroid.data.db.entity.WereWondering
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddWereWonderingViewModel constructor(
    private val dao: WereWonderingDao

) : ViewModel() {

    var allWereWondering: StateFlow<List<WereWondering>> = this.dao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )


    fun addWereWondering(wereWonderingFilmId: Int, nameFilm: String, urlFilm: String, genre: String) {
        viewModelScope.launch {
            dao.insert(
                WereWondering(
                    wereWonderingFilmId = wereWonderingFilmId,
                    nameFilm = nameFilm,
                    urlFilm = urlFilm,
                    genre = genre

                )
            )
        }
    }
    fun deleteWereWondering(wereWonderingFilmId: Int, nameFilm: String, urlFilm: String, genre: String) {
        viewModelScope.launch {
            dao.delete(WereWondering(
                wereWonderingFilmId = wereWonderingFilmId,
                nameFilm = nameFilm,
                urlFilm = urlFilm,
                genre = genre

            ))
        }
    }


}