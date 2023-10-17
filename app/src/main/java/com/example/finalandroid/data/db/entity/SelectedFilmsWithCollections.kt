package com.example.finalandroid.data.db.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class SelectedFilmsWithCollections(
    @Embedded
    val selectedFilms: SelectedFilms,
    @Relation(
        parentColumn = "selected_films_id",
        entityColumn = "collections_id",
        associateBy = Junction(CollectionsSelectedFilmsCrossRef::class)
    )
    val collections: List<Collections>
)
