package com.example.finalandroid.presentation.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.finalandroid.R
import com.example.finalandroid.databinding.FragmentLoaderBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Loader : Fragment() {

    private var _binding: FragmentLoaderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoaderBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            next()
        }
    }
    suspend fun next(){
        delay(2000)
        findNavController().navigate(R.id.homePage)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}