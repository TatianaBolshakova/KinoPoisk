package com.example.finalandroid.presentation.search.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.finalandroid.R
import com.example.finalandroid.data.adapters.YearAdapter
import com.example.finalandroid.databinding.FragmentMyDialogBinding



private const val YEAR1 = "year1"
private const val YEAR2 = "year2"

class MyDialogYear : Fragment() {

    private val yearAdapter = YearAdapter { year -> onItemClick1(year) }
    private val yearAdapter2 = YearAdapter { year -> onItemClick2(year) }

    private var _binding: FragmentMyDialogBinding? = null
    private val binding get() = _binding!!
    private var year1 = 0
    private var year2 = 0
    private var year1Pref: SharedPreferences? = null
    private var year2Pref: SharedPreferences? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            yearUp.adapter = yearAdapter
            yearAdapter.setData()
            yearUp.scrollToPosition(yearAdapter.data.size-965)

            next.setOnClickListener { yearUp.smoothScrollBy(450, 0) }
            back.setOnClickListener { yearUp.smoothScrollBy(-450, 0) }

            yearTo.adapter = yearAdapter2
            yearAdapter2.setData()
            yearTo.scrollToPosition(yearAdapter2.data.size-955)

            next2.setOnClickListener { yearTo.smoothScrollBy(450, 0) }
            back2.setOnClickListener { yearTo.smoothScrollBy(-450, 0) }

           save.setOnClickListener { findNavController().navigate(R.id.action_myDialog_to_filterFragment) }
           iconBack.setOnClickListener {  findNavController().navigate(R.id.action_myDialog_to_filterFragment)}
        }
        year1Pref = this.activity?.getSharedPreferences("YEAR_1", Context.MODE_PRIVATE)
        year1 = year1Pref?.getInt("YEAR1", 1998)!!
        binding.textYearUp.text = year1.toString()

        year2Pref = this.activity?.getSharedPreferences("YEAR_2", Context.MODE_PRIVATE)
        year2 = year2Pref?.getInt("YEAR2", 2023)!!
        binding.textYearTo.text = year2.toString()
    }
    private fun saveYear1Boolean(result: Boolean) {
        val editor = year1Pref?.edit()
        editor?.putBoolean("valueClickYear1", result)
        editor?.apply()
    }
    private fun saveYear2Boolean(result: Boolean) {
        val editor = year2Pref?.edit()
        editor?.putBoolean("valueClickYear2", result)
        editor?.apply()
    }
    private fun saveYear1Value(year1: Int) {
        val editor = year1Pref?.edit()
        editor?.putInt("YEAR1", year1)
        editor?.apply()
    }
    private fun saveYear2Value(year2: Int) {
        val editor = year2Pref?.edit()
        editor?.putInt("YEAR2", year2)
        editor?.apply()
    }
    private fun onItemClick1(item: Int) {

        year1 = item
        saveYear1Boolean(true)
        saveYear1Value(year1)
        binding.textYearUp.text = year1.toString()
    }

    private fun onItemClick2(item: Int) {

        year2 = item
        saveYear2Boolean(true)
        saveYear2Value(year2)
        binding.textYearTo.text = year2.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}