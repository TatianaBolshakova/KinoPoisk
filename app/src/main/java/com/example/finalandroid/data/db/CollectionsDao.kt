package com.example.finalandroid.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.finalandroid.data.db.entity.Collections
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionsDao {
    @Query("SELECT * FROM collections")
    fun getAllCollections(): Flow<List<Collections>>
    @Query("SELECT collections_name FROM collections")
    fun getListNameCollections(): Array<String>
    @Transaction
    @Query("SELECT * FROM collections WHERE collections_id = :collectionsId")
    suspend fun getCollectionsWithSelectedFilms(collectionsId: Long): Collections

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollection(collection: Collections): Long

    @Update
    suspend fun updateCollection(collection: Collections): Int

    @Delete
    suspend fun deleteCollection(collection: Collections): Int
}