package com.example.finalandroid.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "selected_films")
data class SelectedFilms(
    @PrimaryKey
    @ColumnInfo(name  = "selected_films_id")
    val selectedFilmsId: Long = 0,
    @ColumnInfo(name = "name_collection")
    val nameCollection: String,
    @ColumnInfo(name = "name_film")
    val nameFilm: String,
    @ColumnInfo(name = "url_film")
    val urlFilm: String,
    val isLike:Boolean = false,
    val isIWantToSee:Boolean = false
)
