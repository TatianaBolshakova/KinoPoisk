package com.example.finalandroid.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.finalandroid.data.db.entity.IWantToSeeFilms
import kotlinx.coroutines.flow.Flow

@Dao
interface IWantToSeeDao {
    @Query("SELECT * FROM i_want_to_see_films")
    fun getAll(): Flow<List<IWantToSeeFilms>>
    @Insert(entity = IWantToSeeFilms::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(iWantToSeeFilms: IWantToSeeFilms)

    @Delete
    suspend fun delete(iWantToSeeFilms: IWantToSeeFilms)

}