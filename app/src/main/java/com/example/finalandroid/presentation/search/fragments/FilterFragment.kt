package com.example.finalandroid.presentation.search.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.finalandroid.R
import com.example.finalandroid.databinding.FragmentFilterBinding
import com.google.android.material.slider.RangeSlider


private const val TYPE = "type"
private const val COUNTRY = "country"
private const val GENRE = "genre"
private const val YEAR1 = "year1"
private const val YEAR2 = "year2"
private const val RATING1 = "rating1"
private const val RATING2 = "rating2"

class FilterFragment : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private var type = "ALL"
    private var country = "Россия"
    private var genre = "Комедия"
    private var year1 = 0
    private var year2 = 0
    private var rating1 = 0
    private var rating2 = 0
    private var countryPref: SharedPreferences? = null
    private var genrePref: SharedPreferences? = null
    private var year1Pref: SharedPreferences? = null
    private var year2Pref: SharedPreferences? = null
    private var rating1Pref: SharedPreferences? = null
    private var rating2Pref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            year1 = it.getInt(YEAR1)
            year2 = it.getInt(YEAR2)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countryPref = this.activity?.getSharedPreferences("COUNTRY", Context.MODE_PRIVATE)
        country = countryPref?.getString("Country", "Россия")!!
        binding.textDialogCountry.text = country

        genrePref = this.activity?.getSharedPreferences("GENRE", Context.MODE_PRIVATE)
        genre = genrePref?.getString("Genre", "Комедия")!!
        binding.textDialogGenre.text = genre

        year1Pref = this.activity?.getSharedPreferences("YEAR_1", Context.MODE_PRIVATE)
        year1 = year1Pref?.getInt("YEAR1", 1998)!!
        year2Pref = this.activity?.getSharedPreferences("YEAR_2", Context.MODE_PRIVATE)
        year2 = year2Pref?.getInt("YEAR2", 2023)!!
        val textDialogYear = " С $year1 до $year2"
        binding.textDialogYear.text = textDialogYear

        rating1Pref = this.activity?.getSharedPreferences("RATING_1", Context.MODE_PRIVATE)
        rating1 = rating1Pref?.getInt("RATING1", 0)!!
        rating2Pref = this.activity?.getSharedPreferences("RATING_2", Context.MODE_PRIVATE)
        rating2 = rating2Pref?.getInt("RATING2", 10)!!
        val textDialogRating = "От $rating1 до $rating2"
        binding.textDialogRating.text = textDialogRating

        binding.bnAll.setOnClickListener {
            binding.apply {
                bnAll.setImageResource(R.drawable.ic_all)
                bnFilms.setImageResource(R.drawable.ic_films_disabled)
                bnFilms.background.setTint(Color.WHITE)
                bnTvSeries.setImageResource(R.drawable.ic_tv_series_disabled)
                bnTvSeries.background.setTint(Color.WHITE)
            }
            type = "ALL"
        }
        binding.bnFilms.setOnClickListener {
            binding.apply {
                bnAll.setImageResource(R.drawable.ic_all_disabled)
                bnFilms.setImageResource(R.drawable.ic_films)
                bnFilms.background.setTint(Color.BLUE)
                bnTvSeries.setImageResource(R.drawable.ic_tv_series_disabled)
                bnTvSeries.background.setTint(Color.WHITE)
            }
            type = "FILM"
        }
        binding.bnTvSeries.setOnClickListener {
            binding.apply {
                bnTvSeries.setImageResource(R.drawable.ic_tv_series)
                bnTvSeries.background.setTint(Color.BLUE)
                bnAll.setImageResource(R.drawable.ic_all_disabled)
                bnFilms.setImageResource(R.drawable.ic_films_disabled)
                bnFilms.background.setTint(Color.WHITE)
            }
            type = "TV_SERIES"
        }
        binding.dialogCountry.setOnClickListener { choiceCountryDialog() }
        binding.dialogGenre.setOnClickListener { choiceGenreDialog() }
        binding.dialogYear.setOnClickListener {
            findNavController().navigate(R.id.action_filterFragment_to_myDialog)
        }
        binding.rangeSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {}

            override fun onStopTrackingTouch(slider: RangeSlider) {}
        })
        binding.rangeSlider.addOnChangeListener { _, _, _ ->
            val values = binding.rangeSlider.values
            val text = "От ${values[0]} до ${values[1]}"
            binding.textDialogRating.text = text
            rating1 = values[0].toInt()
            saveRating1(rating1)
            rating2 = values[1].toInt()
            saveRating2(rating2)

        }
        binding.iconBack.setOnClickListener {
            val bundle = Bundle().apply {
                putString(TYPE, type)
                putString(COUNTRY, country)
                putString(GENRE, genre)
                putInt(YEAR1, year1)
                putInt(YEAR2, year2)
                putInt(RATING1, rating1)
                putInt(RATING2, rating2)
            }
            findNavController().navigate(
                R.id.action_filterFragment_to_navigation_search,
                args = bundle
            )
        }
    }
    private val countryArray = arrayOf("Россия", "Великобритания", "США", "Германия", "Франция")
    private fun choiceCountryDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Выберите страну")
            .setItems(countryArray) { _, wich ->
                binding.textDialogCountry.text = countryArray[wich]
                country = countryArray[wich]
                saveCountry(country)
            }
        builder.show()

    }

    private val genreArray = arrayOf("Комедия", "Мелодрама", "Боевик", "Вестерн", "Драма")
    private fun choiceGenreDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Выберите жанр")
            .setItems(genreArray) { _, wich ->
                binding.textDialogGenre.text = genreArray[wich]
                genre = genreArray[wich]
                saveGenre(genre)
            }
        builder.show()
    }
    private fun saveCountry(country: String) {
        val editor = countryPref?.edit()
        editor?.putString("Country", country)
        editor?.apply()
    }
    private fun saveGenre(genre: String) {
        val editor = genrePref?.edit()
        editor?.putString("Genre", genre)
        editor?.apply()
    }
    private fun saveRating1(rating: Int) {
        val editor = rating1Pref?.edit()
        editor?.putInt("RATING1", rating)
        editor?.apply()
    }
    private fun saveRating2(rating: Int) {
        val editor = rating2Pref?.edit()
        editor?.putInt("RATING2", rating)
        editor?.apply()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}