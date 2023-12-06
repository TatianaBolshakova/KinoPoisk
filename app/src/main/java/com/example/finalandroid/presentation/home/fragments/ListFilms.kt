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
import com.example.finalandroid.data.adapters.ActorsAdapter
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.adapters.MoviePagedListAdapter
import com.example.finalandroid.data.adapters.MyLoadStateAdapter
import com.example.finalandroid.data.adapters.SimilarsAdapter
import com.example.finalandroid.data.adapters.WorkedOnTheFilmAdapter
import com.example.finalandroid.data.constsnts.Constants
import com.example.finalandroid.data.models.InfoActorsItem
import com.example.finalandroid.databinding.ListFilmsBinding
import com.example.finalandroid.presentation.home.viewmodel.ActorsViewModel
import com.example.finalandroid.presentation.home.viewmodel.PremiersViewModel
import com.example.finalandroid.presentation.home.viewmodel.RandomTypeViewModel
import com.example.finalandroid.presentation.home.viewmodel.SimilarsViewModel
import com.example.finalandroid.presentation.home.viewmodel.ThrillersViewModel
import com.example.finalandroid.presentation.home.viewmodel.TopMovieViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class ListFilms : Fragment() {
    private var _binding: ListFilmsBinding? = null
    private val binding get() = _binding!!
    private val vmTop: TopMovieViewModel by viewModels()
    private val vmPremiers: PremiersViewModel by viewModels()
    private val vmTvSeries: RandomTypeViewModel by viewModels()
    private val vmThrillers: ThrillersViewModel by viewModels()
    private val vmActors: ActorsViewModel by viewModels()
    private val vmSimilars: SimilarsViewModel by viewModels()
    private val topMovieAdapter = MoviePagedListAdapter { movie -> onItemClick1(movie) }
    private val premiersAdapter = MoviePagedListAdapter { movie -> onItemClick(movie) }
    private val randomTypeAdapter = MoviePagedListAdapter { movie -> onItemClick(movie) }
    private val thrillersAdapter = MoviePagedListAdapter { movie -> onItemClick(movie) }
    private val actorsAdapter = ActorsAdapter { actors -> onItemClick(actors) }
    private val workedOnTheFilmAdapter = WorkedOnTheFilmAdapter { actors -> onItemClick(actors) }
    private val similarsAdapter = SimilarsAdapter { movie -> onMovieClick(movie) }
    private var id = 0
    var name: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(Constants.NAME).toString()
            id = it.getInt(Constants.ID_FILM)
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
        binding.iconBack.setOnClickListener { findNavController().popBackStack() }
        if (name == Constants.TOP_250_RU) {
            binding.recyclerTv.adapter = topMovieAdapter.withLoadStateFooter(MyLoadStateAdapter())
            vmTop.pagedMovies.onEach { topMovieAdapter.submitData(it) }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
        if (name == Constants.NAME_PREMIERS) {
            binding.recyclerTv.adapter = premiersAdapter.withLoadStateFooter(MyLoadStateAdapter())
            vmPremiers.pagedMovies.onEach { premiersAdapter.submitData(it) }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
        if (name == Constants.RANDOM_TYPE) {

            binding.recyclerTv.adapter = randomTypeAdapter.withLoadStateFooter(MyLoadStateAdapter())
            vmTvSeries.pagedMovies.onEach { randomTypeAdapter.submitData(it) }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
        if (name == Constants.NAME_THRILLERS) {
            binding.recyclerTv.adapter = thrillersAdapter.withLoadStateFooter(MyLoadStateAdapter())
            vmThrillers.pagedMovies.onEach { thrillersAdapter.submitData(it) }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }


        if (name == Constants.ALL_ACTORS) {
            vmActors.loadInfo(id)
            binding.apply {
                recyclerActors.visibility = View.VISIBLE
                recyclerActors.adapter = actorsAdapter

                vmActors.actors.onEach {
                    actorsAdapter.setData(it)
                }
                    .launchIn(viewLifecycleOwner.lifecycleScope)
            }
        }
        if (name == Constants.WORKED_ON_THE_FILM) {
            vmActors.loadInfo(id)
            binding.recyclerActors.visibility = View.VISIBLE
            binding.recyclerActors.adapter = workedOnTheFilmAdapter
            vmActors.actors.onEach {
                workedOnTheFilmAdapter.setData(it)
            }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
        if (name == Constants.NAME_SIMILARS) {
            vmSimilars.loadInfo(id)
            binding.apply {
                recyclerTv.adapter = similarsAdapter
                vmSimilars.movie.onEach { similarsAdapter.setData(it) }
                    .launchIn(viewLifecycleOwner.lifecycleScope)
            }
        }
    }

    private fun onItemClick(item: Movie) {
        val bundle = Bundle().apply {
            putInt(Constants.ID_FILM, item.kinopoiskId)
        }
        findNavController().navigate(R.id.filmPage, args = bundle)
    }

    private fun onItemClick1(item: Movie) {
        val bundle = Bundle().apply {
            putInt(Constants.ID_FILM, item.filmId)
        }
        findNavController().navigate(R.id.filmPage, args = bundle)
    }

    private fun onItemClick(item: InfoActorsItem) {
        val bundle = Bundle().apply {
            putInt(Constants.ID_ACTOR, item.staffId)
            putInt(Constants.ID_FILM, id)
        }
        findNavController().navigate(R.id.actorPage, args = bundle)
    }

    private fun onMovieClick(item: Movie) {

        val bundle = Bundle().apply {
            putInt(Constants.ID_FILM, item.filmId)
        }
        findNavController().navigate(R.id.filmPage, args = bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}