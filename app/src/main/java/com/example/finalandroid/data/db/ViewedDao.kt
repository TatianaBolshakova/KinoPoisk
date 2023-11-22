package com.example.finalandroid.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.finalandroid.data.db.entity.ViewedFilms
import kotlinx.coroutines.flow.Flow

@Dao
interface ViewedDao {
    @Query("SELECT * FROM viewed_films")
    fun getAll(): Flow<List<ViewedFilms>>
    @Insert(entity = ViewedFilms::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(viewedFilms: ViewedFilms)

    @Query("DELETE FROM viewed_films")
    suspend fun delete()
//    @Query("UPDATE viewed_films SET numberOfRepetitions = numberOfRepetitions+1 ")
//    suspend fun update()

}