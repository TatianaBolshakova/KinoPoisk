package com.example.finalandroid.domain.repository


import com.example.finalandroid.data.db.entity.SelectedFilms

interface LikeFilmRepository{

    suspend fun getInfoFilm(id: Long, nameFilm: String, urlFilm: String): SelectedFilms
}