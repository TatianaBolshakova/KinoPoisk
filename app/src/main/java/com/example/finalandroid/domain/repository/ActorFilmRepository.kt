package com.example.finalandroid.domain.repository



import com.example.finalandroid.data.models.Movie

interface ActorFilmRepository {

    suspend fun getActorFilm(id: Int): List<Movie>

}