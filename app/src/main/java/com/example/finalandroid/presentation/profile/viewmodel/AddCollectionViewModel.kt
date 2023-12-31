package com.example.finalandroid.presentation.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalandroid.data.db.CollectionsDao
import com.example.finalandroid.data.db.entity.Collections
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddCollectionViewModel constructor(
    private val dao: CollectionsDao

) : ViewModel() {

    var allCollections: StateFlow<List<Collections>> = this.dao.getAllCollections()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun addCollection(name: String, icon: Int) {
        viewModelScope.launch {
            dao.insertCollection(
                Collections(
                    collectionsName = name,
                    collectionsIcon = icon,
                )
            )

        }
    }

}