package com.example.finalandroid.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["collections_id","selected_films_id"])
data class CollectionsSelectedFilmsCrossRef(
    @ColumnInfo(name = "collections_id")
    val collectionsId: Long,
    @ColumnInfo(name  = "selected_films_id")
    val selectedFilmsId: Long
)
