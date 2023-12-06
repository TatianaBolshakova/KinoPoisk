package com.example.finalandroid.presentation.profile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.finalandroid.R
import com.example.finalandroid.data.adapters.IWantToSeeFilmAdapter
import com.example.finalandroid.data.adapters.LikeFilmAdapter
import com.example.finalandroid.data.constsnts.Constants
import com.example.finalandroid.data.db.App
import com.example.finalandroid.data.db.CollectionsDao
import com.example.finalandroid.data.db.IWantToSeeDao
import com.example.finalandroid.data.db.LikeDao
import com.example.finalandroid.data.db.entity.IWantToSeeFilms
import com.example.finalandroid.data.db.entity.LikeFilms
import com.example.finalandroid.databinding.ListFilmsCollectionBinding
import com.example.finalandroid.presentation.profile.viewmodel.AddLikeFilmViewModel
import com.example.finalandroid.presentation.profile.viewmodel.AddIWantToSeeViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach



class ListFilmsCollection : Fragment() {
    private var _binding: ListFilmsCollectionBinding? = null
    private val binding get() = _binding!!


    private val vmLikeFilm: AddLikeFilmViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val likeDao: LikeDao = (activity?.application as App).db.likeDao()
                val collectionDao: CollectionsDao =
                    (activity?.application as App).db.collectionsDao()
                return AddLikeFilmViewModel(likeDao, collectionDao) as T
            }
        }
    }
    private val vmIWantToSee: AddIWantToSeeViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dao: IWantToSeeDao = (activity?.application as App).db.iWantToSeeDao()
                return AddIWantToSeeViewModel(dao) as T
            }
        }
    }

    private val likeFilmAdapter = LikeFilmAdapter { movie -> onLikeFilmClick(movie) }
    private val iWantToSeeFilmAdapter =
        IWantToSeeFilmAdapter { movie -> onIWantToSeeFilmClick(movie) }

    var name: String = "Name"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(Constants.NAME).toString()

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListFilmsCollectionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tv.text = name
        binding.iconBack.setOnClickListener { findNavController().navigate(R.id.navigation_profile) }


        if (name == Constants.NAME_COLLECTION_LIKE) {
            binding.recyclerTv.adapter = likeFilmAdapter
            vmLikeFilm.allSelectedFilm.onEach {
                likeFilmAdapter.setData(it)
                binding.count.text = it.size.toString()
            }
                .launchIn(viewLifecycleOwner.lifecycleScope)

        }
        if (name ==Constants.NAME_COLLECTION_I_WANT_TO_SEE) {
            binding.recyclerTv.adapter = iWantToSeeFilmAdapter
            vmIWantToSee.allSelectedFilm.onEach {
                iWantToSeeFilmAdapter.setData(it)
                binding.count.text = it.size.toString()
            }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    private fun onLikeFilmClick(item: LikeFilms) {
        val bundle = Bundle().apply {
            putInt(Constants.ID_FILM, item.likeFilmId)
        }
        findNavController().navigate(R.id.filmPage, args = bundle)
    }

    private fun onIWantToSeeFilmClick(item: IWantToSeeFilms) {
        val bundle = Bundle().apply {
            putInt(Constants.ID_FILM, item.iWantToSeeFilmId)
        }
        findNavController().navigate(R.id.filmPage, args = bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}