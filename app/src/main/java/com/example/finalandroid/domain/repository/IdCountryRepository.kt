package com.example.finalandroid.domain.repository


import com.example.finalandroid.data.models.Country

interface IdCountryRepository{

    suspend fun idAndCountries(): Array<Country>
}
