package com.example.finalandroid.presentation.profile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.finalandroid.R
import com.example.finalandroid.data.adapters.LikeFilmAdapter
import com.example.finalandroid.data.db.App
import com.example.finalandroid.data.db.SelectedFilmsDao
import com.example.finalandroid.data.db.entity.SelectedFilms
import com.example.finalandroid.databinding.ListFilmsCollectionBinding
import com.example.finalandroid.presentation.profile.viewmodel.AddFilmViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val NAME = "name_collection"
private const val ID_FILM = "film_id"
private const val NAME_COLLECTION_LIKE= "Любимые"
class ListFilmsCollection : Fragment() {
    private var _binding: ListFilmsCollectionBinding? = null
    private val binding get() = _binding!!


    private val vmLikeFilm: AddFilmViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dao: SelectedFilmsDao = (activity?.application as App).db.selectedFilmsDao()
                return AddFilmViewModel(dao) as T
            }
        }
    }

    private val likeFilmAdapter = LikeFilmAdapter { movie -> onLikeFilmClick(movie) }

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
        _binding = ListFilmsCollectionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tv.text = name
        Toast.makeText(requireContext(), "${name} -nameCollection ", Toast.LENGTH_SHORT).show()
        binding.iconBack.setOnClickListener { findNavController().navigate(R.id.navigation_profile) }


        if (name== NAME_COLLECTION_LIKE){
            binding.recyclerTv.adapter = likeFilmAdapter
            vmLikeFilm.allSelectedFilm.onEach { likeFilmAdapter.setData(it) }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }



    }

    private fun onLikeFilmClick(item: SelectedFilms) {
        val bundle = Bundle().apply {
            putInt(ID_FILM, item.selectedFilmsId.toInt())
        }
        findNavController().navigate(R.id.filmPage, args = bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}