package com.example.finalandroid.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.finalandroid.data.db.entity.LikeFilms
import kotlinx.coroutines.flow.Flow

@Dao
interface LikeDao {
    @Query("SELECT * FROM like_films")
    fun getAll(): Flow<List<LikeFilms>>
    @Insert(entity = LikeFilms::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(likeFilms: LikeFilms)

    @Delete
    suspend fun delete(likeFilms: LikeFilms)

}