package com.example.finalandroid.data.repository


import com.example.finalandroid.data.api.retrofit
import com.example.finalandroid.data.models.Item
import com.example.finalandroid.data.models.Video
import com.example.finalandroid.domain.repository.VideoRepository

class VideoRepositoryImpl: VideoRepository {

   override suspend fun getVideo(id: Int):List<Item>{
        return retrofit.video(id).items
    }

}