package com.example.finalandroid.presentation.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.finalandroid.R
import com.example.finalandroid.data.adapters.ImagesAdapter
import com.example.finalandroid.data.models.Items
import com.example.finalandroid.databinding.FragmentFilmPageBinding
import com.example.finalandroid.databinding.FragmentGalleryBinding
import com.example.finalandroid.presentation.home.viewmodel.ImagesViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val ID_FILM = "film_id"
private const val ITEM_IMAGE = "item_image"

class Gallery : Fragment() {

    private val imagesAdapter = ImagesAdapter { images -> onImageClick(images) }
    private val vmImages: ImagesViewModel by viewModels()
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ID_FILM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vmImages.loadInfo(id)
        binding.recyclerImage.adapter = imagesAdapter
        vmImages.images.onEach {
            imagesAdapter.setData(it)
        }
            .launchIn(viewLifecycleOwner.lifecycleScope)
        binding.iconBack.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(ID_FILM, id)
            }
            findNavController().navigate(R.id.filmPage, args = bundle)
        }
    }
    private fun onImageClick(item: Items) {
        val bundle = Bundle().apply {
            putString(ITEM_IMAGE, item.imageUrl)
            putInt(ID_FILM, id)
        }
        findNavController().navigate(R.id.imagePage, args = bundle)
    }

}