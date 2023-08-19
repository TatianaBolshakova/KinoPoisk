package com.example.finalandroid.data.repository


import com.example.finalandroid.data.models.InfoActorItem

import com.example.finalandroid.data.api.retrofit
import com.example.finalandroid.domain.repository.ActorRepository

class ActorRepositoryImpl: ActorRepository {

   override suspend fun getActors(id: Int): InfoActorItem {
        return retrofit.actor(id)
    }

}