package com.example.finalandroid.presentation.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.finalandroid.R
import com.example.finalandroid.data.adapters.ActorFilmAllAdapter
import com.example.finalandroid.data.adapters.ActorFilmBestAdapter
import com.example.finalandroid.data.constsnts.Constants
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.databinding.FragmentActorPageBinding
import com.example.finalandroid.presentation.home.viewmodel.ActorFilmViewModel
import com.example.finalandroid.presentation.home.viewmodel.ActorViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ActorPage : Fragment() {

    private var _binding: FragmentActorPageBinding? = null
    private val binding get() = _binding!!
    private var idActor: Int = 0
    private var idFilm: Int = 0
    private var nameActor: String = ""
    private val vmActor: ActorViewModel by viewModels()
    private val vmActorFilm: ActorFilmViewModel by viewModels()
    private val filmBestAdapter = ActorFilmBestAdapter { film -> onItemClick(film) }
    private val filmAllAdapter = ActorFilmAllAdapter { film -> onItemClick(film) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idActor = it.getInt(Constants.ID_ACTOR)
            idFilm = it.getInt(Constants.ID_FILM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActorPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vmActor.loadInfo(idActor)

        lifecycleScope.launch {
            vmActor.actor
                .collect {
                    nameActor = it?.nameRu.toString()
                    binding.nameActors.text = nameActor
                    binding.prof.text = it?.profession

                    Glide.with(this@ActorPage)
                        .load(it?.posterUrl)
                        .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                        .into(binding.imageActors)
                }
        }

        binding.iconBack.setOnClickListener {
            findNavController().popBackStack()
        }

        vmActorFilm.loadInfo(idActor)
        binding.recyclerBest.adapter = filmBestAdapter
        vmActorFilm.actor.onEach {
            filmBestAdapter.setData(it)
            binding.all.text = filmBestAdapter.itemCount.toString()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.all.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(Constants.ID_ACTOR, idActor)
                putString(Constants.NAME_ACTOR, nameActor)
                putString(Constants.NAME_PAGE, Constants.BEST)
            }
            findNavController().navigate(R.id.name_page, args = bundle)
        }

        binding.recyclerAllFilmActor.adapter = filmAllAdapter
        vmActorFilm.actor.onEach {
            filmAllAdapter.setData(it)
            binding.list.text = it.size.toString()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.list.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(Constants.ID_ACTOR, idActor)
                putString(Constants.NAME_ACTOR, nameActor)
                putString(Constants.NAME_PAGE, Constants.FILMOGRAPHY)
            }
            findNavController().navigate(R.id.name_page, args = bundle)
        }
    }

    private fun onItemClick(item: Movie) {
        val bundle = Bundle().apply {
            putInt(Constants.ID_FILM, item.filmId)
        }
        findNavController().navigate(R.id.filmPage, args = bundle)
    }

}