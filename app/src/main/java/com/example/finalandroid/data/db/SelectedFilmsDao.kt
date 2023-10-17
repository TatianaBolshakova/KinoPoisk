package com.example.finalandroid.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.finalandroid.data.db.entity.SelectedFilms
import com.example.finalandroid.data.db.entity.SelectedFilmsWithCollections
import kotlinx.coroutines.flow.Flow

@Dao
interface SelectedFilmsDao {
    @Query("SELECT * FROM selected_films")
    fun getAllSelectedFilms(): Flow<List<SelectedFilms>>

    @Transaction
    @Query("SELECT * FROM selected_films WHERE selected_films_id = :selectedFilmsId")
    suspend fun getSelectedFilmsWithCollections(selectedFilmsId: Long): SelectedFilmsWithCollections

    @Query("SELECT * FROM selected_films WHERE selected_films_id IN (:selectedFilmsId)")
    suspend fun getSelectedFilmsByIds(selectedFilmsId: List<Long>): List<SelectedFilms>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSelectedFilm(selectedFilm: SelectedFilms): Long

    @Update
    suspend fun updateSelectedFilm(selectedFilm: SelectedFilms): Int

    @Delete
    suspend fun deleteSelectedFilm(selectedFilm: SelectedFilms): Int
}