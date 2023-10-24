package com.example.finalandroid.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.finalandroid.data.db.entity.WereWondering
import kotlinx.coroutines.flow.Flow

@Dao
interface WereWonderingDao {
    @Query("SELECT * FROM were_wondering")
    fun getAll(): Flow<List<WereWondering>>
    @Insert(entity = WereWondering::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wereWondering: WereWondering)

    @Query("DELETE FROM were_wondering")
    suspend fun delete()

}