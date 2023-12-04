package com.example.finalandroid.presentation.home.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.finalandroid.R
import com.example.finalandroid.databinding.FragmentOnboarding1Binding

class Onboarding1 : Fragment() {
    private var _binding: FragmentOnboarding1Binding? = null
    private val binding get() = _binding!!
    private var isStart = false
    private var pref: SharedPreferences? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboarding1Binding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = this.activity?.getSharedPreferences(
            "START",
            Context.MODE_PRIVATE
        )
        val valueStart = pref?.getBoolean("Start", false)!!
        if (isStart==valueStart){
            saveStart(true)
        } else{
            findNavController().navigate(R.id.homePage)
        }



        binding.textViewNext.setOnClickListener {
            findNavController().navigate(R.id.onboarding2)
        }
        binding.ellipse2.setOnClickListener {
            findNavController().navigate(R.id.onboarding2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun saveStart(result: Boolean) {
        val editor = pref?.edit()
        editor?.putBoolean("Start", result)
        editor?.apply()
    }
}