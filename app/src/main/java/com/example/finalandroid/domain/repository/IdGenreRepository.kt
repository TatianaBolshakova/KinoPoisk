package com.example.finalandroid.domain.repository



import com.example.finalandroid.data.models.Genre

interface IdGenreRepository{

    suspend fun idAndGenre(): Array<Genre>

}
