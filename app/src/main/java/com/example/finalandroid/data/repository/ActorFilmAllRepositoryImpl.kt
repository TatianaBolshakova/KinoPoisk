package com.example.finalandroid.data.repository


import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.api.retrofit
import com.example.finalandroid.domain.repository.ActorFilmAllRepository

class ActorFilmAllRepositoryImpl : ActorFilmAllRepository {

   override suspend fun getActorFilm(id: Int): List<Movie> {
        return retrofit.actor(id).films
    }

}