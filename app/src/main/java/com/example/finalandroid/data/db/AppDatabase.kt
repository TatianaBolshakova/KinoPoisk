package com.example.finalandroid.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.finalandroid.data.db.entity.Collections
import com.example.finalandroid.data.db.entity.CollectionsSelectedFilmsCrossRef
import com.example.finalandroid.data.db.entity.SelectedFilms
import com.example.finalandroid.data.db.entity.ViewedFilms
import com.example.finalandroid.data.db.entity.WereWondering


@Database(
    entities = [
        Collections::class,
        SelectedFilms::class,
        CollectionsSelectedFilmsCrossRef::class,
        ViewedFilms::class,
        WereWondering::class
    ], version = 1
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun collectionsDao(): CollectionsDao
    abstract fun selectedFilmsDao(): SelectedFilmsDao
    abstract fun viewedDao(): ViewedDao
    abstract fun wereWonderingDao(): WereWonderingDao
}