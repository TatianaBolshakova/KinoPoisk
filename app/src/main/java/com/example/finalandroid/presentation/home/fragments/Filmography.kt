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
import com.example.finalandroid.data.adapters.ActorFilmBestListAdapter
import com.example.finalandroid.data.adapters.FilmographyAdapter
import com.example.finalandroid.data.constsnts.Constants
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.databinding.FragmentFilmographyBinding
import com.example.finalandroid.presentation.home.viewmodel.ActorFilmViewModel
import com.example.finalandroid.presentation.home.viewmodel.ActorViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class Filmography : Fragment() {
    private var _binding: FragmentFilmographyBinding? = null
    private val binding get() = _binding!!
    private val vmActorFilmAll: ActorFilmViewModel by viewModels()
    private val vmActor: ActorViewModel by viewModels()
    private val filmographyAdapter = FilmographyAdapter { film -> onItemClick(film) }
    private val bestAdapter = ActorFilmBestListAdapter { film -> onItemClick(film) }
    private var idActor: Int = 0
    private var nameActor: String = ""
    private var namePage: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idActor = it.getInt(Constants.ID_ACTOR)
            nameActor = it.getString(Constants.NAME_ACTOR).toString()
            namePage = it.getString(Constants.NAME_PAGE).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmographyBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nameActor.text = nameActor
        binding.namePage.text = namePage
        vmActorFilmAll.loadInfo(idActor)
        if (namePage == Constants.FILMOGRAPHY) {
            binding.recyclerFilmography.adapter = filmographyAdapter
            vmActorFilmAll.actor.onEach {
                filmographyAdapter.setData(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
            vmActor.loadInfo(idActor)
            lifecycleScope.launch {
                vmActor.actor.collect {
                }
            }
        }
        if (namePage == Constants.BEST) {
            binding.recyclerFilmography.adapter = bestAdapter
            vmActorFilmAll.actor.onEach { bestAdapter.setData(it) }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }

        binding.iconBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun onItemClick(item: Movie) {
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