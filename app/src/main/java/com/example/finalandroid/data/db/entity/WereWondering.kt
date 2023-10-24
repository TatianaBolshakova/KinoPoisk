package com.example.finalandroid.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "were_wondering")
data class WereWondering(
    @PrimaryKey
    @ColumnInfo(name = "were_wondering_film_id")
    val wereWonderingFilmId: Int,
    @ColumnInfo(name = "name_film")
    val nameFilm: String,
    @ColumnInfo(name = "url_film")
    val urlFilm: String,
    @ColumnInfo(name = "genre")
    val genre: String
)