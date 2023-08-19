package com.example.finalandroid.data.repository


import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.api.retrofit
import com.example.finalandroid.domain.repository.ActorFilmBestRepository

class ActorFilmBestRepositoryImpl(): ActorFilmBestRepository {

   override suspend fun getActorFilm(id: Int): List<Movie> {
        return retrofit.actorFilm(id).films
    }

}