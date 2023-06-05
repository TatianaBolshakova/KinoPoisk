package com.example.finalandroid.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.premiersmovielist.*
import com.example.finalandroid.data.popularspagedmovielist.MoviePagedListAdapter
import com.example.finalandroid.data.popularspagedmovielist.MoviePagedViewModel
import com.example.finalandroid.data.popularspagedmovielist.MyLoadStateAdapter
import com.example.finalandroid.databinding.FragmentHomePageBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
private const val IMAGE_ITEM = "image_item"
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HomePage : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!
    private val viewModelPremiers: MoviePremiersViewModel by viewModels()
    private val viewModelPopulars: MoviePagedViewModel by viewModels()

    private val movieAdapter = MovieAdapter()
    private val moviePopularsAdapter = MoviePagedListAdapter {movie-> onItemClick(movie)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomePageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerPremiers.adapter = movieAdapter
        binding.recyclerPopulars.adapter = moviePopularsAdapter.withLoadStateFooter(MyLoadStateAdapter())

        viewModelPremiers.movie.onEach {
            movieAdapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)



        viewModelPopulars.pagedMovie.onEach {
            moviePopularsAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onItemClick(item: Movie) {
        val bundle = Bundle().apply {
            putString(IMAGE_ITEM, item.posterUrl)
        }
        //findNavController().navigate(R.id.SecondFragment, args = bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}