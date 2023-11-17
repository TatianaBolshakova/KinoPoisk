package com.example.finalandroid.presentation.search.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalandroid.data.models.Country
import com.example.finalandroid.data.repository.IdCountryRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class IdCountryViewModel private constructor(
    private val repository: IdCountryRepositoryImpl
) : ViewModel() {
    constructor() : this(IdCountryRepositoryImpl())

    private val _idCountry = MutableStateFlow<Array<Country>>(emptyArray())
    val idCountry = _idCountry.asStateFlow()


    fun loadIdCountry() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                repository.idAndCountries()
            }.fold(
                onSuccess = { _idCountry.value = it },
                onFailure = { Log.d("IdCountryViewModel", it.message ?: "")}
            )

        }
    }
}
