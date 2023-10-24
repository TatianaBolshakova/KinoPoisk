package com.example.finalandroid.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "viewed_films")
data class ViewedFilms (
    @PrimaryKey
    @ColumnInfo(name = "viewed_film_id")
    val viewedFilmId: Int,
    @ColumnInfo(name = "name_film")
    val nameFilm: String,
    @ColumnInfo(name = "url_film")
    val urlFilm: String,
    @ColumnInfo(name = "genre")
    val genre: String
)