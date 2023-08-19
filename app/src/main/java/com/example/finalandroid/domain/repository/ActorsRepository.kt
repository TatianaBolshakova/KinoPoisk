package com.example.finalandroid.domain.repository


import com.example.finalandroid.data.models.InfoActorsItem

interface ActorsRepository {

    suspend fun getActors(filmId: Int):List<InfoActorsItem>
}