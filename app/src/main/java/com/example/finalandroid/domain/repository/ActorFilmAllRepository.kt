package com.example.finalandroid.domain.repository



import com.example.finalandroid.data.models.Movie

interface ActorFilmAllRepository {

    suspend fun getActorFilm(id: Int): List<Movie>

}