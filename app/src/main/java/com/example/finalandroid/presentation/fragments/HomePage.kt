package com.example.finalandroid.presentation.fragments

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
import com.example.finalandroid.data.adapters.HomePageAdapter
import com.example.finalandroid.data.adapters.MovieAdapter
import com.example.finalandroid.presentation.viewmodel.PremiersViewModel
import com.example.finalandroid.presentation.viewmodel.RandomTypeViewModel
import com.example.finalandroid.presentation.viewmodel.ThrillersViewModel
import com.example.finalandroid.presentation.viewmodel.TopMovieViewModel
import com.example.finalandroid.databinding.FragmentHomePageBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val ID_FILM = "film_id"
private const val NAME = "name"
class HomePage : Fragment() {

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!

    private val vmPremiers: PremiersViewModel by viewModels()
    private val vmThrillers: ThrillersViewModel by viewModels()
    private val vmRandomType: RandomTypeViewModel by viewModels()
    private val vmTop: TopMovieViewModel by viewModels()

    private val premiersAdapter = MovieAdapter { movie -> onItemClick(movie) }
    private val thrillersAdapter = MovieAdapter { movie -> onItemClick(movie) }
    private val randomTypeAdapter = MovieAdapter { movie -> onItemClick(movie) }
    private val topMovieAdapter = MovieAdapter { movie -> onItemClick(movie) }
    private val homePageAdapter = HomePageAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomePageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            recyclerPremiers.adapter = premiersAdapter
            recyclerThrillers.adapter = thrillersAdapter
            recyclerTvSeries.adapter = randomTypeAdapter
            recyclerTop.adapter = topMovieAdapter
        }

        val nameRandomType = when (vmRandomType.type) {
            "FILM" -> "Фильмы"
            "TV_SHOW" -> "Тв-шоу"
            "TV_SERIES" -> "Сериалы"
            "MINI_SERIES" -> "Мини-сериалы"
            else -> "Все"
        }

        val nameRandomTop = when (vmTop.type) {
            "TOP_250_BEST_FILMS"-> "ТОП 250 ЛУЧШИХ ФИЛЬМОВ"
            "TOP_100_POPULAR_FILMS"-> "ТОП 100 ПОПУЛЯРНЫХ ФИЛЬМОВ"
            else->"ТОП: ОЖИДАЕМЫЕ ФИЛЬМЫ"
        }
        binding.tvSeries.text = nameRandomType
        binding.topList.text = nameRandomTop

        vmPremiers.movie.onEach { premiersAdapter.setData(it) }.launchIn(viewLifecycleOwner.lifecycleScope)
        vmThrillers.movie.onEach { thrillersAdapter.setData(it) }.launchIn(viewLifecycleOwner.lifecycleScope)
        vmRandomType.movie.onEach { randomTypeAdapter.setData(it) }.launchIn(viewLifecycleOwner.lifecycleScope)
        vmTop.movie.onEach { topMovieAdapter.setData(it) }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.allPremiers.setOnClickListener {
            val bundle = Bundle().apply { putString(NAME, "Премьеры") }
            findNavController().navigate(R.id.listFilms, args = bundle) }
        binding.allThrillers.setOnClickListener {
            val bundle = Bundle().apply { putString(NAME, "Триллеры") }
            findNavController().navigate(R.id.listFilms, args = bundle) }
        binding.allTopList.setOnClickListener {
            val bundle = Bundle().apply { putString(NAME, nameRandomTop) }
            findNavController().navigate(R.id.listFilms, args = bundle) }

        binding.allTvSeries.setOnClickListener {
            val bundle = Bundle().apply { putString(NAME, nameRandomType) }
            findNavController().navigate(R.id.listFilms, args = bundle)
        }
    }

    private fun onItemClick(item: Movie) {
        val bundle = Bundle().apply { putInt(ID_FILM, item.kinopoiskId) }
        findNavController().navigate(R.id.filmPage, args = bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}