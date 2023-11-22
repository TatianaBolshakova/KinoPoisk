package com.example.finalandroid.presentation.search.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.isEmpty
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.finalandroid.R
import com.example.finalandroid.data.adapters.SearchMovieAdapter
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.databinding.FragmentSearchBinding
import com.example.finalandroid.presentation.search.viewmodel.SearchViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val ID_FILM = "film_id"
private const val NAME_FILM = "name_film"
private const val URL_FILM = "url_film"
private const val GENRE_FILM = "genre"
private const val TYPE = "type"
private const val COUNTRY_ID = "country_id"
private const val GENRE_ID = "genre_id"
private const val YEAR1 = "year1"
private const val YEAR2 = "year2"
private const val RATING1 = "rating1"
private const val RATING2 = "rating2"
private const val ORDER = "order"

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val vmSearch: SearchViewModel by viewModels()
    private val searchKeyWordAdapter = SearchMovieAdapter { movie -> onItemClick(movie) }
    private var type = "ALL"
    private var countryId = 1
    private var countryIdArray = arrayOf(countryId)
    private var genreId = 1
    private var genreIdArray = arrayOf(genreId)
    private var year1 = 2003
    private var year2 = 2023
    private var rating1 = 0
    private var rating2 = 10
    private var order = "RATING"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getString(TYPE).toString()
            genreId = it.getInt(GENRE_ID)
            countryId = it.getInt(COUNTRY_ID)
            year1 = it.getInt(YEAR1)
            year2 = it.getInt(YEAR2)
            rating1 = it.getInt(RATING1)
            rating2 = it.getInt(RATING2)
            order = it.getString(ORDER).toString()
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

        genreIdArray[0] = genreId
        countryIdArray[0] = countryId
        vmSearch.loadMovie(
            type = type,
            country = countryIdArray,
            genre = countryIdArray,
            year1 = year1,
            year2 = year2,
            rating1 = rating1,
            rating2 = rating2,
            order = order
        )

        binding.recyclerSearchResult.adapter = searchKeyWordAdapter
        vmSearch.movie.onEach {
            searchKeyWordAdapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.search.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(keyword: String?): Boolean {

                vmSearch.loadMovie(
                    keyword.toString(),
                    type = type,
                    country = countryIdArray,
                    genre = genreIdArray,
                    year1 = year1,
                    year2 = year2,
                    rating1 = rating1,
                    rating2 = rating2,
                    order = order
                )
                binding.apply {
                    recyclerSearchResult.adapter = searchKeyWordAdapter
                    vmSearch.movie.onEach {
                        searchKeyWordAdapter.setData(it)
                        if (recyclerSearchResult.isEmpty()) {
                            progress.visibility = View.VISIBLE
                            delay(1000)
                            progress.visibility = View.GONE
                            textSearchNoResult.visibility = View.VISIBLE
                            textSearchNoResult.text =
                                "К сожалению, по Вашему запросу ничего не найдено"
                        } else {
                            searchKeyWordAdapter.setData(it)
                            textSearchNoResult.visibility = View.GONE
                        }
                    }.launchIn(viewLifecycleOwner.lifecycleScope)
                }
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
            putString(GENRE_FILM, item.genres?.joinToString { genre -> genre.genre })
        }
        findNavController().navigate(R.id.filmPage, args = bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


