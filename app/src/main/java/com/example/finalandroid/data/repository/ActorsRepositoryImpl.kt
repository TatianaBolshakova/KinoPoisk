package com.example.finalandroid.data.repository


import com.example.finalandroid.data.models.InfoActorsItem
import com.example.finalandroid.data.api.retrofit
import com.example.finalandroid.domain.repository.ActorsRepository

class ActorsRepositoryImpl: ActorsRepository {

   override suspend fun getActors(filmId: Int):List<InfoActorsItem>{
        return retrofit.allActors(filmId)
    }

}