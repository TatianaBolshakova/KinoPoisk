package com.example.finalandroid.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "like_films")
data class LikeFilms (
    @PrimaryKey
    @ColumnInfo(name = "like_film_id")
    val likeFilmId: Int,
    @ColumnInfo(name = "name_film")
    val nameFilm: String,
    @ColumnInfo(name = "url_film")
    val urlFilm: String,
    @ColumnInfo(name = "genre")
    val genre: String
)