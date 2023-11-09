package com.example.finalandroid.presentation.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.finalandroid.R
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.adapters.MovieAdapter
import com.example.finalandroid.databinding.ListFilmsBinding
import com.example.finalandroid.presentation.home.viewmodel.PremiersViewModel
import com.example.finalandroid.presentation.home.viewmodel.RandomTypeViewModel
import com.example.finalandroid.presentation.home.viewmodel.ThrillersViewModel
import com.example.finalandroid.presentation.home.viewmodel.TopMovieViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val TOP_250_RU = "ТОП 250 ЛУЧШИХ ФИЛЬМОВ"
private const val TOP_100_RU = "ТОП 100 ПОПУЛЯРНЫХ ФИЛЬМОВ"
private const val TOP_AWAIT_RU = "ТОП: ОЖИДАЕМЫЕ ФИЛЬМЫ"
private const val FILM_RU = "Фильмы"
private const val TV_SHOW_RU = "Тв-шоу"
private const val TV_SERIES_RU = "Сериалы"
private const val MINI_SERIES_RU = "Мини-сериалы"
private const val ALL_RU = "Все"
private const val NAME_PREMIERS = "Премьеры"
private const val NAME_THRILLERS = "Триллеры"
private const val ID_FILM = "film_id"
private const val NAME = "name"


class ListFilms : Fragment() {
    private var _binding: ListFilmsBinding? = null
    private val binding get() = _binding!!

    private val vmTop: TopMovieViewModel by viewModels()
    private val vmPremiers: PremiersViewModel by viewModels()
    private val vmTvSeries: RandomTypeViewModel by viewModels()
    private val vmThrillers: ThrillersViewModel by viewModels()



    private val topMovieAdapter = MovieAdapter { movie -> onItemClick1(movie) }
    private val premiersAdapter = MovieAdapter { movie -> onItemClick(movie) }
    private val randomTypeAdapter = MovieAdapter { movie -> onItemClick(movie) }
    private val thrillersAdapter = MovieAdapter { movie -> onItemClick(movie) }

    var name: String = "Name"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(NAME).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListFilmsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tv.text = name
        binding.iconBack.setOnClickListener { findNavController().navigate(R.id.homePage) }
        if (name == TOP_100_RU || name == TOP_250_RU || name == TOP_AWAIT_RU) {
            binding.recyclerTv.adapter = topMovieAdapter
            vmTop.movie.onEach { topMovieAdapter.setData(it) }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
        if (name == NAME_PREMIERS) {
            binding.recyclerTv.adapter = premiersAdapter
            vmPremiers.movie.onEach { premiersAdapter.setData(it) }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
        if (name == FILM_RU || name == TV_SHOW_RU || name == TV_SERIES_RU || name == MINI_SERIES_RU || name == ALL_RU) {

            binding.recyclerTv.adapter = randomTypeAdapter
            vmTvSeries.movie.onEach { randomTypeAdapter.setData(it) }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
        if (name == NAME_THRILLERS) {
            binding.recyclerTv.adapter = thrillersAdapter
            vmThrillers.movie.onEach { thrillersAdapter.setData(it) }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    private fun onItemClick(item: Movie) {
        val bundle = Bundle().apply {
            putInt(ID_FILM, item.kinopoiskId)
        }
        findNavController().navigate(R.id.filmPage, args = bundle)
    }

    private fun onItemClick1(item: Movie) {
        val bundle = Bundle().apply {
            putInt(ID_FILM, item.filmId)
        }
        findNavController().navigate(R.id.filmPage, args = bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}