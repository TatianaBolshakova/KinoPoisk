package com.example.finalandroid.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.finalandroid.data.db.entity.Collections
import com.example.finalandroid.data.db.entity.CollectionsSelectedFilmsCrossRef
import com.example.finalandroid.data.db.entity.SelectedFilms


@Database(
    entities = [
        Collections::class,
        SelectedFilms::class,
        CollectionsSelectedFilmsCrossRef::class
    ], version = 1
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun collectionsDao(): CollectionsDao
    abstract fun selectedFilmsDao(): SelectedFilmsDao
}