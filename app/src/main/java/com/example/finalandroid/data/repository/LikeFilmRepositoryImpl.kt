package com.example.finalandroid.data.repository



import com.example.finalandroid.data.db.entity.SelectedFilms
import com.example.finalandroid.domain.repository.LikeFilmRepository

class LikeFilmRepositoryImpl: LikeFilmRepository {

    override suspend fun getInfoFilm(id: Long, nameCollection: String, nameFilm: String, urlFilm: String): SelectedFilms {
        return SelectedFilms(id,nameCollection,nameFilm, urlFilm)
    }

}