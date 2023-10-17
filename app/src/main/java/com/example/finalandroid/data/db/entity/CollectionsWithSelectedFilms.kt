package com.example.finalandroid.data.db.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CollectionsWithSelectedFilms(
    @Embedded
    val collections: Collections,
    @Relation(
        parentColumn = "collections_id",
        entityColumn = "selected_films_id",
        associateBy = Junction(CollectionsSelectedFilmsCrossRef::class)
    )
    val selectedFilms: List<SelectedFilms>
)
