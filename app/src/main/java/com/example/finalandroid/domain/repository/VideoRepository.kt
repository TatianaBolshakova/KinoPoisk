package com.example.finalandroid.domain.repository


import com.example.finalandroid.data.models.Item
import com.example.finalandroid.data.models.Video

interface VideoRepository{

    suspend fun getVideo(id: Int): List<Item>
}