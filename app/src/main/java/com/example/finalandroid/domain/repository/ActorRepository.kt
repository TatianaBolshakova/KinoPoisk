package com.example.finalandroid.domain.repository

import com.example.finalandroid.data.models.InfoActorItem

interface ActorRepository {
    suspend fun getActors(id: Int): InfoActorItem
}