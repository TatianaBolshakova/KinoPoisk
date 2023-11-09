package com.example.finalandroid.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "i_want_to_see_films")
data class IWantToSeeFilms (
    @PrimaryKey
    @ColumnInfo(name = "i_want_to_see_film_id")
    val iWantToSeeFilmId: Int,
    @ColumnInfo(name = "name_film")
    val nameFilm: String,
    @ColumnInfo(name = "url_film")
    val urlFilm: String,
    @ColumnInfo(name = "genre")
    val genre: String
)