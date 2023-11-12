package com.example.finalandroid.presentation.search.fragments

sealed class State {
    object Click: State()
    object NoClick: State()
}