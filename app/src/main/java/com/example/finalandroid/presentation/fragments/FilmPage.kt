package com.example.finalandroid.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.finalandroid.R
import com.example.finalandroid.data.adapters.ActorsAdapter
import com.example.finalandroid.presentation.viewmodel.ActorsViewModel
import com.example.finalandroid.data.adapters.WorkedOnTheFilmAdapter
import com.example.finalandroid.presentation.viewmodel.FilmViewModel
import com.example.finalandroid.data.adapters.ImagesAdapter
import com.example.finalandroid.presentation.viewmodel.ImagesViewModel
import com.example.finalandroid.data.models.InfoActorsItem
import com.example.finalandroid.data.models.Items
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.adapters.SimilarsAdapter
import com.example.finalandroid.presentation.viewmodel.SimilarsViewModel
import com.example.finalandroid.databinding.FragmentFilmPageBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

private const val ID_ACTOR = "actor_id"
private const val ID_FILM = "film_id"
private const val ITEM_IMAGE = "item_image"
class FilmPage : Fragment() {

    private var _binding: FragmentFilmPageBinding? = null
    private val binding get() = _binding!!
    private val vmFilm: FilmViewModel by viewModels()
    private val vmActors: ActorsViewModel by viewModels()
    private val vmImages: ImagesViewModel by viewModels()
    private val vmSimilars: SimilarsViewModel by viewModels()
    private val actorsAdapter = ActorsAdapter { actors -> onItemClick(actors) }
    private val workedOnTheFilmAdapter = WorkedOnTheFilmAdapter { actors -> onItemClick(actors) }
    private val imagesAdapter = ImagesAdapter { images -> onImageClick(images) }
    private val similarsAdapter = SimilarsAdapter { movie -> onMovieClick(movie) }

    private var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ID_FILM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vmFilm.loadInfo(id)
        with(binding) {
        lifecycleScope.launch {
            vmFilm.info
                .collect { info ->

                    description1.text = info?.description
                    description2.text = info?.shortDescription

                    Glide.with(this@FilmPage)
                        .load(info?.posterUrl)
                        .into(imageMovie)
                }
        }}
        binding.iconBack.setOnClickListener { findNavController().navigate(R.id.homePage) }

        vmActors.loadInfo(id)
        binding.recyclerActors.adapter = actorsAdapter
        vmActors.actors.onEach { actorsAdapter.setData(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        binding.recyclerWorkedOnTheFilm.adapter = workedOnTheFilmAdapter
        vmActors.actors.onEach { workedOnTheFilmAdapter.setData(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        vmImages.loadInfo(id)
        binding.recyclerImages.adapter = imagesAdapter
        vmImages.images.onEach { imagesAdapter.setData(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        vmSimilars.loadInfo(id)
        binding.recyclerSimilars.adapter = similarsAdapter
        vmSimilars.movie.onEach { similarsAdapter.setData(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onImageClick(item: Items) {
        val bundle = Bundle().apply {
            putString(ITEM_IMAGE, item.imageUrl)
            putInt(ID_FILM, id)
        }
        findNavController().navigate(R.id.imagePage, args = bundle)
    }

    private fun onItemClick(item: InfoActorsItem) {
        val bundle = Bundle().apply {
            putInt(ID_ACTOR, item.staffId)
            putInt(ID_FILM, id)
        }
        findNavController().navigate(R.id.actorPage, args = bundle)
    }

    private fun onMovieClick(item: Movie) {

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