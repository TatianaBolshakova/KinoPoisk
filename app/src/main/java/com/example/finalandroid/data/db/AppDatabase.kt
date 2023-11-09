package com.example.finalandroid.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.finalandroid.data.db.entity.Collections
import com.example.finalandroid.data.db.entity.IWantToSeeFilms
import com.example.finalandroid.data.db.entity.LikeFilms
import com.example.finalandroid.data.db.entity.ViewedFilms
import com.example.finalandroid.data.db.entity.WereWondering


@Database(
    entities = [
        Collections::class,
        LikeFilms::class,
        IWantToSeeFilms::class,
        ViewedFilms::class,
        WereWondering::class
    ], version = 1
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun collectionsDao(): CollectionsDao
   abstract fun likeDao(): LikeDao
    abstract fun iWantToSeeDao(): IWantToSeeDao
    abstract fun viewedDao(): ViewedDao
    abstract fun wereWonderingDao(): WereWonderingDao
}