package com.example.finalandroid.presentation.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalandroid.data.db.CollectionsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListNameCollectionViewModel constructor(
    private val dao: CollectionsDao

) : ViewModel() {

    private val _list = MutableStateFlow<Array<String>>(emptyArray())
    val list = _list.asStateFlow()
    fun selectCollection() {
        viewModelScope.launch(Dispatchers.IO)  {
           _list.value =  dao.getListNameCollections()

        }
    }
}