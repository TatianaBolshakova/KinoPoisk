package com.example.finalandroid.presentation.profile.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.finalandroid.R
import com.example.finalandroid.data.db.App
import com.example.finalandroid.data.db.CollectionsDao
import com.example.finalandroid.data.db.entity.CollectionsWithSelectedFilms
import com.example.finalandroid.databinding.FragmentAddCollectionBinding
import com.example.finalandroid.presentation.home.fragments.FilmPage
import com.example.finalandroid.presentation.profile.viewmodel.AddCollectionViewModel


class AddCollectionFragment : Fragment() {
    // TODO: Rename and change types of parameters
val filmPage = FilmPage()
    private var _binding: FragmentAddCollectionBinding? = null
    private val binding get() = _binding!!
   // private val likeFilmAdapter = LikeFilmAdapter{ movie -> onLikeFilmClick(movie) }

    private val vmAddCollection: AddCollectionViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dao: CollectionsDao = (activity?.application as App).db.collectionsDao()
                return AddCollectionViewModel(dao) as T
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


         binding.saveCollectionInDb.setOnClickListener {


            vmAddCollection.addCollection(
                name = binding.enterNameCollection.text.toString(),
                icon = R.drawable.my_collection,
                numberOfElements = filmPage.countFilm
            )
         findNavController().navigate(R.id.navigation_profile)}
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCollectionBinding.inflate(inflater)
        return binding.root

    }
    private fun onLikeFilmClick(item: CollectionsWithSelectedFilms) {

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}