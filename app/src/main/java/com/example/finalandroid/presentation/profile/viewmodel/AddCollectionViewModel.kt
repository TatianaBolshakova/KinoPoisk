package com.example.finalandroid.presentation.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalandroid.data.db.CollectionsDao
import com.example.finalandroid.data.db.entity.Collections
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
private const val NAME_COLLECTION_LIKE = "Любимые"
private const val NAME_COLLECTION_I_WANT_TO_SEE = "Хочу посмотреть"
class AddCollectionViewModel constructor(
    private val dao: CollectionsDao

) : ViewModel() {
//    fun defaultCollections() {
//        addCollection(name = NAME_COLLECTION_LIKE, icon = R.drawable.ic_like_disabled, numberOfElements = 0)
//       addCollection(name = NAME_COLLECTION_I_WANT_TO_SEE, icon = R.drawable.i_want_to_see, numberOfElements = 0)
//    }

    var allCollections: StateFlow<List<Collections>> = this.dao.getAllCollections()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )



    fun addCollection(name: String, icon: Int, numberOfElements: Int) {
        viewModelScope.launch {
            dao.insertCollection(
                Collections(
                    collectionsName = name,
                    collectionsIcon = icon,
                    numberOfElements = numberOfElements
                )
            )

        }
    }




//    fun addCollection(name: String, icon: Int, numberOfElements: Int) {
//        viewModelScope.launch {
//            dao.insertCollection(
//               CollectionWithSelectedFilm(
//                   collection = Collection(nameCollection = name),
//                   parametersCollection = ParametersCollection(iconCollection = icon, numberOfElements = numberOfElements),
//                   selectedFilm = emptyList()
//               )
//            )
//
//        }
//    }
}