package com.example.finalandroid.presentation.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalandroid.data.db.SelectedFilmsDao
import com.example.finalandroid.data.db.entity.SelectedFilms
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddFilmViewModel constructor(
    private val dao: SelectedFilmsDao

) : ViewModel() {

    var allSelectedFilm: StateFlow<List<SelectedFilms>> = this.dao.getAllSelectedFilms()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun addFilm(
        id: Long,
        nameFilm: String,
        urlFilm: String
    ) {
        viewModelScope.launch {
            dao.insertSelectedFilm(
                SelectedFilms(
                    selectedFilmsId = id,
                    nameFilm = nameFilm,
                    urlFilm = urlFilm,
                )
            )

        }
    }
    fun deleteFilm(
        id: Long,
        nameFilm: String,
        urlFilm: String
    ) {
        viewModelScope.launch {
            dao.deleteSelectedFilm(
                SelectedFilms(
                    selectedFilmsId = id,
                    nameFilm = nameFilm,
                    urlFilm = urlFilm,
                )
            )

        }
    }
//    fun addFilm(
//collections: Collections, selectedFilms: List<SelectedFilms>
//    ) {
//        viewModelScope.launch {
//            dao.insertSelectedFilms(
//                CollectionsWithSelectedFilms(
//                   collections, selectedFilms))
//
//        }
//    }
//    fun addIWantToSeeFilm(
//        id: Int,
//        nameFilm: String,
//        urlFilm: String
//    ) {
//        viewModelScope.launch {
//            dao.insertIWantToSeeFilm(
//                IWantToSeeFilm(
//                    id = id,
//                    nameFilm = nameFilm,
//                    urlFilm = urlFilm,
//                )
//            )
//
//        }
//    }

//    fun deleteFilm(
//        collections: Collections, selectedFilms: SelectedFilms
//    ) {
//        viewModelScope.launch {
//            dao.deleteSelectedFilms(
//                CollectionsWithSelectedFilms(
//                    collections, selectedFilms
//                )
//            )
//        }
//    }

//    fun deleteIWantToSeeFilm(
//        id: Int,
//        nameFilm: String,
//        urlFilm: String
//    ) {
//        viewModelScope.launch {
//            dao.deleteIWantToSeeFilm(
//                IWantToSeeFilm(
//                    id = id,
//                    nameFilm = nameFilm,
//                    urlFilm = urlFilm,
//                )
//            )
//        }
//    }

//val listTest  = emptyList<SelectedFilm>()
//    fun addCollection() {
//        viewModelScope.launch {
//            dao.insert(
//                Collection(
//                    nameCollection = "name",
//                    iconCollection = "icon",
//                    numberOfElements = listTest.size-1,
//                    selectedFilm = listTest
//                )
//            )
//        }
//    }

//    fun deleteLike(id: Int, name: String, url: String) {
//        viewModelScope.launch {
//            dao.delete(
//                SelectedFilm(
//                    id = id,
//                    name = name,
//                    url = url
//                )
//            )
//        }
//    }

    fun addIWantToSee() {}
    fun addAlreadyViewed() {}
    fun share() {}
    fun openAdditionalMenu() {}


}