package com.example.finalandroid.data.repository



import com.example.finalandroid.data.api.retrofit
import com.example.finalandroid.data.models.Country
import com.example.finalandroid.domain.repository.IdCountryRepository

class IdCountryRepositoryImpl : IdCountryRepository {
    override suspend fun idAndCountries(

    ): Array<Country> {
        return retrofit.idAndCountries().countries
    }
}