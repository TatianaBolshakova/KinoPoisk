package com.example.finalandroid.data.repository



import com.example.finalandroid.data.models.Items
import com.example.finalandroid.data.api.retrofit
import com.example.finalandroid.domain.repository.ImagesRepository

class ImagesRepositoryImpl : ImagesRepository {

   override suspend fun getImages(id: Int): List<Items> {
        return retrofit.images(id).items
    }

}