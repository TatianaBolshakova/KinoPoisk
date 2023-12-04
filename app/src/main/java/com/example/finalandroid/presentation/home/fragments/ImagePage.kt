package com.example.finalandroid.presentation.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.finalandroid.R
import com.example.finalandroid.databinding.FragmentImagePageBinding
private const val ITEM_IMAGE = "item_image"
private const val ID_FILM = "film_id"
class ImagePage : Fragment() {
    // TODO: Rename and change types of parameters
    private var image: String? = null
    private var idFilm = 0
    private var _binding: FragmentImagePageBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            image = it.getString(ITEM_IMAGE)
            idFilm = it.getInt(ID_FILM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImagePageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this@ImagePage)
            .load(image)
            .into(binding.imageView2)

        binding.iconBack.setOnClickListener {
//            val bundle = Bundle().apply {
//                putInt(ID_FILM, idFilm)
//            }
            findNavController().popBackStack()}
    }

}