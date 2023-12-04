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
import com.example.finalandroid.data.adapters.MoviePagedListAdapter
import com.example.finalandroid.data.adapters.MyLoadStateAdapter
import com.example.finalandroid.databinding.FragmentHomePageBinding
import com.example.finalandroid.presentation.home.viewmodel.PremiersViewModel
import com.example.finalandroid.presentation.home.viewmodel.RandomTypeViewModel
import com.example.finalandroid.presentation.home.viewmodel.ThrillersViewModel
import com.example.finalandroid.presentation.home.viewmodel.TopMovieViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val ID_FILM = "film_id"
private const val NAME_FILM = "name_film"
private const val URL_FILM = "url_film"
private const val GENRE = "genre"
private const val NAME = "name"
private const val NAME_PREMIERS = "Премьеры"
private const val NAME_THRILLERS = "Триллеры"
private const val TOP_250_RU = "ТОП 250 ЛУЧШИХ ФИЛЬМОВ"
private const val RANDOM_TYPE = "Случайная подборка фильмов"

class HomePage : Fragment() {

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!

    private val vmPremiers: PremiersViewModel by viewModels()
    private val vmThrillers: ThrillersViewModel by viewModels()
    private val vmRandomType: RandomTypeViewModel by viewModels()
    private val vmTop: TopMovieViewModel by viewModels()

    private val premiersAdapter = MoviePagedListAdapter { movie -> onItemClick(movie) }
    private val thrillersAdapter = MoviePagedListAdapter { movie -> onItemClick(movie) }
    private val randomTypeAdapter = MoviePagedListAdapter { movie -> onItemClick(movie) }
    private val topMovieAdapter = MoviePagedListAdapter { movie -> onItemTopClick(movie) }


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
            recyclerPremiers.adapter = premiersAdapter.withLoadStateFooter(MyLoadStateAdapter())
            recyclerThrillers.adapter = thrillersAdapter.withLoadStateFooter(MyLoadStateAdapter())
            recyclerTvSeries.adapter = randomTypeAdapter.withLoadStateFooter(MyLoadStateAdapter())
            recyclerTop.adapter = topMovieAdapter.withLoadStateFooter(MyLoadStateAdapter())
        }
        //val nameRandomType = vmRandomType.nameRandomType

        binding.tvSeries.text = RANDOM_TYPE
        binding.topList.text =TOP_250_RU

        vmPremiers.pagedMovies.onEach { premiersAdapter.submitData(it)}.launchIn(viewLifecycleOwner.lifecycleScope)
        vmThrillers.pagedMovies.onEach { thrillersAdapter.submitData(it)}.launchIn(viewLifecycleOwner.lifecycleScope)
        vmRandomType.pagedMovies.onEach { randomTypeAdapter.submitData(it) }.launchIn(viewLifecycleOwner.lifecycleScope)
        vmTop.pagedMovies.onEach { topMovieAdapter.submitData(it)}.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.apply {
            allPremiers.setOnClickListener {
                val bundle = Bundle().apply { putString(NAME, NAME_PREMIERS) }
                findNavController().navigate(R.id.listFilms, args = bundle)
            }
            allThrillers.setOnClickListener {
                val bundle = Bundle().apply { putString(NAME, NAME_THRILLERS) }
                findNavController().navigate(R.id.listFilms, args = bundle)
            }
            allTopList.setOnClickListener {
                val bundle = Bundle().apply { putString(NAME, TOP_250_RU) }
                findNavController().navigate(R.id.listFilms, args = bundle)
            }

            allTvSeries.setOnClickListener {
                val bundle = Bundle().apply { putString(NAME, RANDOM_TYPE) }
                findNavController().navigate(R.id.listFilms, args = bundle)
            }
        }
    }

    private fun onItemClick(item: Movie) {

        val bundle = Bundle().apply {
            putInt(ID_FILM, item.kinopoiskId)
            putString(NAME_FILM, item.nameRu)
            putString(URL_FILM, item.posterUrl)
            putString(GENRE, item.genres.joinToString { genre -> genre.genre })
        }
        findNavController().navigate(R.id.filmPage, args = bundle)
    }

    private fun onItemTopClick(item: Movie) {
        val bundle = Bundle().apply { putInt(ID_FILM, item.filmId) }
        findNavController().navigate(R.id.filmPage, args = bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}