package com.example.finalandroid.data.repository



import com.example.finalandroid.data.api.retrofit
import com.example.finalandroid.data.models.Genre
import com.example.finalandroid.domain.repository.IdGenreRepository

class IdGenreRepositoryImpl : IdGenreRepository {
    override suspend fun idAndGenre(

    ): Array<Genre> {
        return retrofit.idAndCountries().genres
    }

}