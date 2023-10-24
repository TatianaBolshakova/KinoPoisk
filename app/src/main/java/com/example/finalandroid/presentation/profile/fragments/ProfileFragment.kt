package com.example.finalandroid.presentation.profile.fragments

import android.content.Context
import android.content.SharedPreferences
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
import com.example.finalandroid.data.adapters.CollectionAdapter
import com.example.finalandroid.data.adapters.ViewedFilmsAdapter
import com.example.finalandroid.data.adapters.WereWonderingFilmsAdapter
import com.example.finalandroid.data.db.App
import com.example.finalandroid.data.db.CollectionsDao
import com.example.finalandroid.data.db.ViewedDao
import com.example.finalandroid.data.db.WereWonderingDao
import com.example.finalandroid.data.db.entity.Collections
import com.example.finalandroid.data.db.entity.ViewedFilms
import com.example.finalandroid.data.db.entity.WereWondering
import com.example.finalandroid.databinding.FragmentProfileBinding
import com.example.finalandroid.presentation.profile.viewmodel.AddCollectionViewModel
import com.example.finalandroid.presentation.profile.viewmodel.AddViewedViewModel
import com.example.finalandroid.presentation.profile.viewmodel.AddWereWonderingViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val NAME = "name_collection"
private const val NAME_COLLECTION_LIKE = "Любимые"
private const val NAME_COLLECTION_I_WANT_TO_SEE = "Хочу посмотреть"
private const val ID_FILM = "id"

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var isDefault = false
    private var pref: SharedPreferences? = null
    private val viewedAdapter = ViewedFilmsAdapter { movie -> onMovieViewedClick(movie) }
    private val wereWonderingAdapter =
        WereWonderingFilmsAdapter { movie -> onMovieWereWonderingClick(movie) }
    private val collectionAdapter =
        CollectionAdapter { collection -> onCollectionClick(collection) }

    private val vmAddCollection: AddCollectionViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dao: CollectionsDao = (activity?.application as App).db.collectionsDao()
                return AddCollectionViewModel(dao) as T
            }
        }
    }
    private val vmViewed: AddViewedViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dao: ViewedDao = (activity?.application as App).db.viewedDao()
                return AddViewedViewModel(dao) as T
            }
        }
    }
    private val vmWondering: AddWereWonderingViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dao: WereWonderingDao = (activity?.application as App).db.wereWonderingDao()
                return AddWereWonderingViewModel(dao) as T
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = this.activity?.getSharedPreferences(
            "COLLECTION",
            Context.MODE_PRIVATE
        )
        val valueDefault = pref?.getBoolean("isDefault", false)!!
        if (isDefault == valueDefault) {
            vmAddCollection.addCollection(
                name = NAME_COLLECTION_LIKE,
                icon = R.drawable.ic_like_disabled
            )
            vmAddCollection.addCollection(
                name = NAME_COLLECTION_I_WANT_TO_SEE,
                icon = R.drawable.i_want_to_see
            )
            saveData(true)
        }


        binding.apply {

            recyclerViewed.adapter = viewedAdapter
            vmViewed.allViewedFilms.onEach { viewedAdapter.setData(it) }
                .launchIn(viewLifecycleOwner.lifecycleScope)
            val counterViewedValue = (vmViewed.allViewedFilms.value.size + 1).toString()
            counterViewed.text = counterViewedValue

            addMyCollections.setOnClickListener { findNavController().navigate(R.id.addCollectionFragment) }


            recyclerCollections.adapter = collectionAdapter
            vmAddCollection.allCollections.onEach { collectionAdapter.setData(it) }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            recyclerYouWereWondering.adapter = wereWonderingAdapter
            vmWondering.allWereWondering.onEach { wereWonderingAdapter.setData(it) }
                .launchIn(viewLifecycleOwner.lifecycleScope)
            val counterWereWonderingValue = (vmWondering.allWereWondering.value.size + 1).toString()
            counterYouWereWondering.text = counterWereWonderingValue

        }

    }

    private fun saveData(result: Boolean) {
        val editor = pref?.edit()
        editor?.putBoolean("isDefault", result)
        editor?.apply()
    }


    private fun onCollectionClick(item: Collections) {
        val bundle = Bundle().apply {
            putString(NAME, item.collectionsName)
        }
        findNavController().navigate(R.id.listFilmsCollection, args = bundle)
    }

    private fun onMovieViewedClick(item: ViewedFilms) {

        val bundle = Bundle().apply {
            putInt(ID_FILM, item.viewedFilmId)
        }
        findNavController().navigate(R.id.filmPage, args = bundle)
    }

    private fun onMovieWereWonderingClick(item: WereWondering) {

        val bundle = Bundle().apply {
            putInt(ID_FILM, item.wereWonderingFilmId)
        }
        findNavController().navigate(R.id.filmPage, args = bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
