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
import com.example.finalandroid.data.adapters.ActorFilmAllAdapter
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.databinding.FragmentFilmographyBinding
import com.example.finalandroid.presentation.home.viewmodel.ActorFilmViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val ID_ACTOR = "actor_id"
private const val ID_FILM = "film_id"

class Filmography : Fragment() {
    private var _binding: FragmentFilmographyBinding? = null
    private val binding get() = _binding!!

    private val vmActorFilmAll: ActorFilmViewModel by viewModels()
    private val actorFilmAllAdapter = ActorFilmAllAdapter { film -> onItemClick(film) }
    private var idActor: Int = 0
    private var idFilm: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idActor = it.getInt(ID_ACTOR)
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

        vmActorFilmAll.loadInfo(idActor)
        binding.recyclerFilmography.adapter = actorFilmAllAdapter
        vmActorFilmAll.actor.onEach { actorFilmAllAdapter.setData(it) }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.iconBack.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(ID_ACTOR, idActor)
            }
            findNavController().navigate(R.id.actorPage, args = bundle) }


    }

    private fun onItemClick(item: Movie) {
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