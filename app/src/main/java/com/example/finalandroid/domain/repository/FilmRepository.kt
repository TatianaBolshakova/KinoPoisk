package com.example.finalandroid.domain.repository


import com.example.finalandroid.data.models.InfoMovie

interface FilmRepository{

    suspend fun getInfoFilm(id: Int): InfoMovie
}