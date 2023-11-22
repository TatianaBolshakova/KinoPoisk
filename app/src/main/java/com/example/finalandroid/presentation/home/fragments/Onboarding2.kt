package com.example.finalandroid.presentation.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.finalandroid.R
import com.example.finalandroid.databinding.FragmentOnboarding2Binding

class Onboarding2 : Fragment() {

    private var _binding: FragmentOnboarding2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboarding2Binding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

           binding.textViewNext.setOnClickListener {
               findNavController().navigate(R.id.onboarding3)
           }
           binding.ellipse1.setOnClickListener {
               findNavController().navigate(R.id.onboarding1)
           }
           binding.ellipse3.setOnClickListener {
               findNavController().navigate(R.id.onboarding3)
           }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}