package com.example.finalandroid.presentation.search.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.finalandroid.R
import com.example.finalandroid.data.adapters.MovieAdapter
import com.example.finalandroid.data.adapters.SearchMovieAdapter
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.databinding.FragmentSearchBinding

import com.example.finalandroid.presentation.search.viewmodel.SearchKeyWordViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val ID_FILM = "film_id"
private const val NAME_FILM = "name_film"
private const val URL_FILM = "url_film"
private const val GENRE = "genre"

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val vmSearchKeyWord: SearchKeyWordViewModel by viewModels()
    private val searchKeyWordAdapter = SearchMovieAdapter { movie -> onItemClick(movie) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.search.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(keyword: String?): Boolean {
                vmSearchKeyWord.loadMovie(keyword.toString())
                binding.recyclerSearchResult.adapter = searchKeyWordAdapter
                vmSearchKeyWord.movie.onEach { searchKeyWordAdapter.setData(it) }
                    .launchIn(viewLifecycleOwner.lifecycleScope)
                return true
            }

        })


    }
    private fun onItemClick(item: Movie) {

        val bundle = Bundle().apply {
            putInt(ID_FILM, item.kinopoiskId)
            putString(NAME_FILM, item.nameRu)
            putString(URL_FILM, item.posterUrl)
            putString(GENRE, item.genres[0].genre)
        }
        findNavController().navigate(R.id.filmPage, args = bundle)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


//        binding.searchView1.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(p0: String?): Boolean {
//                return true
//            }
//
//            override fun onQueryTextChange(p0: String?): Boolean {
//               return true
//            }
//   })
//            val closeButtonId: Int = binding.searchView.context.resources.getIdentifier("android:id/search_close_btn", null, null)
//            val closeButton = binding.searchView.findViewById(closeButtonId) as ImageView
//            closeButton.setImageResource(R.drawable.baseline_menu_24)
//
//            closeButton.setOnClickListener {
//                //  клаву прячем
//                binding.searchView.clearFocus()
//                Log.d("MyLog","ты нажал на крестик")
//                true
//            }

//binding.search.inflateMenu(R.menu.searchbar_menu )
//
//        binding.search.setOnMenuItemClickListener {
//            it.
//        }