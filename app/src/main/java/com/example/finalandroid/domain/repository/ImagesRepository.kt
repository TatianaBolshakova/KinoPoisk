package com.example.finalandroid.domain.repository



import com.example.finalandroid.data.models.Items


interface ImagesRepository {

    suspend fun getImages(id: Int): List<Items>

}