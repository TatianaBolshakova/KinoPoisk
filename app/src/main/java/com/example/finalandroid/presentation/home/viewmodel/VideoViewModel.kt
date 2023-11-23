package com.example.finalandroid.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalandroid.data.models.InfoMovie
import com.example.finalandroid.data.models.Item
import com.example.finalandroid.data.models.Video
import com.example.finalandroid.data.repository.FilmRepositoryImpl
import com.example.finalandroid.data.repository.VideoRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VideoViewModel private constructor(

    private val repository: VideoRepositoryImpl,

) : ViewModel() {
    constructor():this(VideoRepositoryImpl())


    private val _info = MutableStateFlow<List<Item>>(emptyList())
    val info = _info.asStateFlow()

    fun loadInfo(id: Int) {
        viewModelScope.launch {
            _info.value = repository.getVideo(id)
        }
    }

}