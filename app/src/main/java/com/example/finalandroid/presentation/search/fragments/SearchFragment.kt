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
import com.example.finalandroid.data.adapters.SearchMovieAdapter
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.databinding.FragmentSearchBinding

import com.example.finalandroid.presentation.search.viewmodel.SearchKeyWordViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val ID_FILM = "film_id"
private const val NAME_FILM = "name_film"
private const val URL_FILM = "url_film"
private const val GENRE_FILM = "genre"

private const val TYPE = "type"
private const val COUNTRY = "country"
private const val GENRE = "genre"
private const val YEAR1 = "year1"
private const val YEAR2 = "year2"
private const val RATING = "rating"

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val vmSearchKeyWord: SearchKeyWordViewModel by viewModels()
    private val searchKeyWordAdapter = SearchMovieAdapter { movie -> onItemClick(movie) }

    private var type = "ALL"
    private var country  = "Россия"
    private var genre = "Комедия"
    private var year1 = "1998"
    private var year2 = "2017"
    private var rating = "Любой"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getString(TYPE).toString()
            country = it.getString(COUNTRY).toString()
            genre = it.getString(GENRE).toString()
            year1 = it.getString(YEAR1).toString()
            year2 = it.getString(YEAR2).toString()
            rating = it.getString(RATING).toString()
        }
    }
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
                vmSearchKeyWord.movie.onEach {
                    searchKeyWordAdapter.setData(it)
//                    if (it.isEmpty()){
//                        binding.textSearchNoResult.text ="К сожалению, по Вашему запросу ничего не найдено"
//                        binding.textSearchNoResult.visibility = View.VISIBLE
//                    }



                }.launchIn(viewLifecycleOwner.lifecycleScope)
//                if (binding.recyclerSearchResult.isEmpty()) {
//                    binding.textSearchNoResult.visibility = View.VISIBLE
//                }
//                else {
//                    binding.textSearchNoResult.visibility = View.GONE
//                }

//                viewLifecycleOwner.lifecycleScope.launch {
//                    vmSearchKeyWord.error.collect {
//                        binding.textSearchNoResult.text = it
//                        Toast.makeText(requireContext(), "$it = text ", Toast.LENGTH_SHORT).show()
//                    }
//                }

                return true
            }

        })



binding.imageButton.setOnClickListener {
    findNavController().navigate(R.id.action_navigation_search_to_filterFragment)
}
 }

    private fun onItemClick(item: Movie) {

        val bundle = Bundle().apply {
            putInt(ID_FILM, item.kinopoiskId)
            putString(NAME_FILM, item.nameRu)
            putString(URL_FILM, item.posterUrl)
            putString(GENRE_FILM, item.genres[0].genre)
        }
        findNavController().navigate(R.id.filmPage, args = bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


