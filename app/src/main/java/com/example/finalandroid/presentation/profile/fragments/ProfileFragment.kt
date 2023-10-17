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
import com.example.finalandroid.data.adapters.DefaultCollectionAdapter
import com.example.finalandroid.data.adapters.LikeFilmAdapter
import com.example.finalandroid.data.db.App
import com.example.finalandroid.data.db.CollectionsDao
import com.example.finalandroid.data.db.entity.Collections
import com.example.finalandroid.data.db.entity.CollectionsWithSelectedFilms
import com.example.finalandroid.data.db.entity.SelectedFilms
import com.example.finalandroid.databinding.CollectionsItemBinding
import com.example.finalandroid.databinding.FragmentProfileBinding
import com.example.finalandroid.presentation.profile.viewmodel.AddCollectionViewModel
import com.example.finalandroid.presentation.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val NAME = "name_collection"
private const val NAME_COLLECTION_LIKE = "Любимые"
private const val NAME_COLLECTION_I_WANT_TO_SEE = "Хочу посмотреть"
private const val COUNT_FILM = "count_film"
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    var isDefault = false
    var pref: SharedPreferences? = null
    var countFilm = 0
    private val likeFilmAdapter = LikeFilmAdapter{ movie -> onLikeFilmClick(movie) }
    private val vmProfile: ProfileViewModel by viewModels()
    private val collectionAdapter =
        CollectionAdapter { collection -> onCollectionClick(collection) }

    private val defaultCollectionAdapter =
        DefaultCollectionAdapter { collection -> onDefaultCollectionClick(collection) }


    private val vmAddCollection: AddCollectionViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dao: CollectionsDao = (activity?.application as App).db.collectionsDao()
                return AddCollectionViewModel(dao) as T
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            countFilm = it.getInt(COUNT_FILM)

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater)
        return binding.root

    }

//    private fun defaultCollections() {
//        vmAddCollection.addCollection(name = NAME_COLLECTION_LIKE, icon = R.drawable.ic_like_disabled, numberOfElements = 0)
//        vmAddCollection.addCollection(name = NAME_COLLECTION_I_WANT_TO_SEE, icon = R.drawable.i_want_to_see, numberOfElements = 0)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // vmAddCollection.defaultCollections()
        pref = this.activity?.getSharedPreferences("COLLECTION",
            Context.MODE_PRIVATE)
        val valueDefault =  pref?.getBoolean("isDefault", false)!!
        if (isDefault==valueDefault){
            vmAddCollection.addCollection(name = NAME_COLLECTION_LIKE, icon = R.drawable.ic_like_disabled, numberOfElements = countFilm)
            vmAddCollection.addCollection(name = NAME_COLLECTION_I_WANT_TO_SEE, icon = R.drawable.i_want_to_see, numberOfElements = countFilm)
            saveData(true)
        }


        binding.apply {
           // recyclerDefaultCollections.adapter = defaultCollectionAdapter


            recyclerCollections.adapter = collectionAdapter
            vmAddCollection.allCollections.onEach { collectionAdapter.setData(it) }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            addMyCollections.setOnClickListener { findNavController().navigate(R.id.addCollectionFragment) }

        }

    }
    fun saveData(result: Boolean){
        val editor = pref?.edit()
        editor?.putBoolean("isDefault", result)
        editor?.apply()
    }
    private fun onDefaultCollectionClick(item: CollectionsItemBinding) {
        val bundle = Bundle().apply {
            putString(NAME, item.textNameCollection.toString())

            //putString(NAME, item.selectedFilm.toString())

        }
        findNavController().navigate(R.id.listFilmsCollection, args = bundle)
    }
    private fun onCollectionClick(item: Collections) {
        val bundle = Bundle().apply {
            putString(NAME, item.collectionsName)

            //putString(NAME, item.selectedFilm.toString())
//            Toast.makeText(requireContext(), "${item.collection.nameCollection} - name ", Toast.LENGTH_SHORT).show()
//            Toast.makeText(requireContext(), "${item.likeFilm} - film ", Toast.LENGTH_SHORT).show()
        }
        findNavController().navigate(R.id.listFilmsCollection, args = bundle)
    }
    private fun onLikeFilmClick(item: SelectedFilms) {

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

//        frameAddLike.setOnClickListener {
//            counter++
//            if (counter % 2 == 1) {
//                vmFilm.info.value?.let {
//                    vmViewed.addLike(
//                        id = it.kinopoiskId,
//                        name = it.nameRu,
//                        url = it.posterUrl
//                    )
//                }
//            } else {
//                vmFilm.info.value?.let {
//                    vmViewed.deleteLike(
//                        id = it.kinopoiskId,
//                        name = it.nameRu,
//                        url = it.posterUrl
//                    )}
//            }
//        }