package com.example.finalandroid.domain.repository


import com.example.finalandroid.data.models.Movie


interface ActorFilmBestRepository {

    suspend fun getActorFilm(id: Int): List<Movie>

}