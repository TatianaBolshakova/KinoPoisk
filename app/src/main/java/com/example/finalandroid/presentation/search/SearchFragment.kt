package com.example.finalandroid.presentation.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import com.example.finalandroid.R
import com.example.finalandroid.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.searchView1.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(p0: String?): Boolean {
//                return true
//            }
//
//            override fun onQueryTextChange(p0: String?): Boolean {
//               return true
//            }
 //   })
//            val closeButtonId: Int = binding.searchView.context.resources.getIdentifier("android:id/search_close_btn", null, null)
//            val closeButton = binding.searchView.findViewById(closeButtonId) as ImageView
//            closeButton.setImageResource(R.drawable.baseline_menu_24)
//
//            closeButton.setOnClickListener {
//                //  клаву прячем
//                binding.searchView.clearFocus()
//                Log.d("MyLog","ты нажал на крестик")
//                true
//            }

//binding.search.inflateMenu(R.menu.searchbar_menu )
//
//        binding.search.setOnMenuItemClickListener {
//            it.
//        }

    }

}