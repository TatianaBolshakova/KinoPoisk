package com.example.finalandroid.presentation.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalandroid.data.db.CollectionsDao
import com.example.finalandroid.data.db.LikeDao
import com.example.finalandroid.data.db.entity.Collections
import com.example.finalandroid.data.db.entity.LikeFilms
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
private const val NAME_COLLECTION_LIKE= "Любимые"
private const val NAME_COLLECTION_I_WANT_TO_SEE = "Хочу посмотреть"
class AddLikeFilmViewModel constructor(
    private val likeDao: LikeDao,
    private val collectionDao: CollectionsDao

) : ViewModel() {

    var allSelectedFilm: StateFlow<List<LikeFilms>> = this.likeDao.getAll()
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
            likeDao.insert(
                LikeFilms(
                    likeFilmId = id,
                    nameFilm = nameFilm,
                    urlFilm = urlFilm,
                    genre = genre,
                )
            )
            collectionDao.updateCollection()

        }
    }
    fun deleteFilm(
        id: Int,
        nameFilm: String,
        urlFilm: String,
        genre: String ,

        ) {
        viewModelScope.launch {
            likeDao.delete(
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