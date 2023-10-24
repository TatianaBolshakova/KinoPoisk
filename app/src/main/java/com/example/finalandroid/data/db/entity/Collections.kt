package com.example.finalandroid.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collections")
data class Collections(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "collections_id")
    val collectionsId: Long = 0,
    @ColumnInfo(name = "collections_name")
    val collectionsName: String,
    @ColumnInfo(name = "collections_icon")
    val collectionsIcon: Int?= null,
    @ColumnInfo(name = "number_of_elements")
    val numberOfElements: Int? = 0
)