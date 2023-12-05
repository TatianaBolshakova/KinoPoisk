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
import com.example.finalandroid.data.constsnts.Constants
import com.example.finalandroid.databinding.FragmentMyDialogBinding


class MyDialogYear : Fragment() {

    private val yearAdapter = YearAdapter { year -> onItemClick1(year) }
    private val yearAdapter2 = YearAdapter { year -> onItemClick2(year) }

    private var _binding: FragmentMyDialogBinding? = null
    private val binding get() = _binding!!
    private var year1 = 0
    private var year2 = 0
    private var prefYear: SharedPreferences? = null

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
            yearUp.scrollToPosition(yearAdapter.data.size - 965)

            next.setOnClickListener { yearUp.smoothScrollBy(450, 0) }
            back.setOnClickListener { yearUp.smoothScrollBy(-450, 0) }

            yearTo.adapter = yearAdapter2
            yearAdapter2.setData()
            yearTo.scrollToPosition(yearAdapter2.data.size - 955)

            next2.setOnClickListener { yearTo.smoothScrollBy(450, 0) }
            back2.setOnClickListener { yearTo.smoothScrollBy(-450, 0) }


        }
        prefYear =
            this.activity?.getSharedPreferences(Constants.NAME_PREF_YEAR, Context.MODE_PRIVATE)
        year1 = prefYear?.getInt(Constants.KEY_YEAR_1, 1998)!!
        binding.textYearUp.text = year1.toString()

        //year2Pref = this.activity?.getSharedPreferences("YEAR_2", Context.MODE_PRIVATE)
        year2 = prefYear?.getInt(Constants.KEY_YEAR_2, 2023)!!
        binding.textYearTo.text = year2.toString()
        binding.iconBack.setOnClickListener {
            saveYearValue(year1, year2)
            findNavController().navigate(R.id.action_myDialog_to_filterFragment)
        }
        binding.save.setOnClickListener {
            saveYearValue(year1, year2)
            findNavController().navigate(R.id.action_myDialog_to_filterFragment)
        }
    }

    private fun saveYearValue(year1: Int, year2: Int) {
        val editor = prefYear?.edit()
        editor?.putInt(Constants.KEY_YEAR_1, year1)
        editor?.putInt(Constants.KEY_YEAR_2, year2)
        editor?.apply()
    }

    private fun onItemClick1(item: Int) {
        year1 = item
        binding.textYearUp.text = year1.toString()
    }

    private fun onItemClick2(item: Int) {
        year2 = item
        binding.textYearTo.text = year2.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}