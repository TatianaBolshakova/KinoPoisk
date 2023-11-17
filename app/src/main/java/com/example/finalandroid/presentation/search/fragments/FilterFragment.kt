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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.finalandroid.R
import com.example.finalandroid.databinding.FragmentFilterBinding
import com.example.finalandroid.presentation.search.viewmodel.IdCountryViewModel
import com.example.finalandroid.presentation.search.viewmodel.IdGenreViewModel
import com.google.android.material.slider.RangeSlider
import kotlinx.coroutines.launch


private const val TYPE = "type"
private const val COUNTRY = "country"
private const val COUNTRY_ID = "country_id"
private const val GENRE = "genre"
private const val GENRE_ID = "genre_id"
private const val YEAR1 = "year1"
private const val YEAR2 = "year2"
private const val RATING1 = "rating1"
private const val RATING2 = "rating2"
private const val ORDER = "order"

class FilterFragment : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private var type = "ALL"
    private var country = "Россия"
    private var countryId = 1
    private var genre = "Комедия"
    private var genreId =1
    private var year1 = 2003
    private var year2 = 2023
    private var rating1 = 0
    private var rating2 = 10
    private var order = "RATING"
    private var countryPref: SharedPreferences? = null
    private var countryIdPref: SharedPreferences? = null
    private var genrePref: SharedPreferences? = null
    private var genreIdPref: SharedPreferences? = null
    private var year1Pref: SharedPreferences? = null
    private var year2Pref: SharedPreferences? = null
    private var rating1Pref: SharedPreferences? = null
    private var rating2Pref: SharedPreferences? = null
    private var textDialogYear = "С ... до ... "
    private var textDialogRating = "Любой"
    private val vmIdCountry: IdCountryViewModel by viewModels()
    private val vmIdGenre: IdGenreViewModel by viewModels()

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

        countryIdPref = this.activity?.getSharedPreferences("COUNTRY_ID", Context.MODE_PRIVATE)
        countryId = countryIdPref?.getInt("CountryId", 1)!!

        genrePref = this.activity?.getSharedPreferences("GENRE", Context.MODE_PRIVATE)
        genre = genrePref?.getString("Genre", "Комедия")!!
        binding.textDialogGenre.text = genre

        genreIdPref = this.activity?.getSharedPreferences("GENRE_ID", Context.MODE_PRIVATE)
        genreId = genreIdPref?.getInt("GenreId", 1)!!

        year1Pref = this.activity?.getSharedPreferences("YEAR_1", Context.MODE_PRIVATE)
        year1 = year1Pref?.getInt("YEAR1", 1998)!!
        year2Pref = this.activity?.getSharedPreferences("YEAR_2", Context.MODE_PRIVATE)
        year2 = year2Pref?.getInt("YEAR2", 2023)!!
        if (year1 < year2) {
            textDialogYear = " С $year1 до $year2"
        } else {
            textDialogYear = " С $year1 до $year1"
            year2=year1
        }
        binding.textDialogYear.text = textDialogYear

        rating1Pref = this.activity?.getSharedPreferences("RATING_1", Context.MODE_PRIVATE)
        rating1 = rating1Pref?.getInt("RATING1", 0)!!
        rating2Pref = this.activity?.getSharedPreferences("RATING_2", Context.MODE_PRIVATE)
        rating2 = rating2Pref?.getInt("RATING2", 10)!!
        textDialogRating = "От $rating1 до $rating2"
        binding.textDialogRating.text = textDialogRating
        binding.apply {
            bnAll.setOnClickListener {
                bnAll.setImageResource(R.drawable.ic_all)
                bnFilms.setImageResource(R.drawable.ic_films_disabled)
                bnFilms.background.setTint(Color.WHITE)
                bnTvSeries.setImageResource(R.drawable.ic_tv_series_disabled)
                bnTvSeries.background.setTint(Color.WHITE)
                type = "ALL"
            }
            bnFilms.setOnClickListener {
                bnAll.setImageResource(R.drawable.ic_all_disabled)
                bnFilms.setImageResource(R.drawable.ic_films)
                bnFilms.background.setTint(Color.BLUE)
                bnTvSeries.setImageResource(R.drawable.ic_tv_series_disabled)
                bnTvSeries.background.setTint(Color.WHITE)
                type = "FILM"
            }
            bnTvSeries.setOnClickListener {
                bnTvSeries.setImageResource(R.drawable.ic_tv_series)
                bnTvSeries.background.setTint(Color.BLUE)
                bnAll.setImageResource(R.drawable.ic_all_disabled)
                bnFilms.setImageResource(R.drawable.ic_films_disabled)
                bnFilms.background.setTint(Color.WHITE)
                type = "TV_SERIES"
            }
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
        binding.apply {
            bnDate.setOnClickListener {
                bnDate.setImageResource(R.drawable.ic_date)
                bnPopulars.setImageResource(R.drawable.ic_populars_disabled)
                bnPopulars.background.setTint(Color.WHITE)
                bnRating.setImageResource(R.drawable.ic_rating_disabled)
                bnRating.background.setTint(Color.WHITE)
                order = "YEAR"
            }
            bnPopulars.setOnClickListener {
                bnDate.setImageResource(R.drawable.ic_date_disabled)
                bnPopulars.setImageResource(R.drawable.ic_populars)
                bnPopulars.background.setTint(Color.BLUE)
                bnRating.setImageResource(R.drawable.ic_rating_disabled)
                bnRating.background.setTint(Color.WHITE)
                order = "NUM_VOTE"
            }
            bnRating.setOnClickListener {
                bnRating.setImageResource(R.drawable.ic_rating)
                bnRating.background.setTint(Color.BLUE)
                bnDate.setImageResource(R.drawable.ic_date_disabled)
                bnPopulars.setImageResource(R.drawable.ic_populars_disabled)
                bnPopulars.background.setTint(Color.WHITE)
                order = "RATING"
            }
        }
        binding.iconBack.setOnClickListener {
            val bundle = Bundle().apply {
                putString(TYPE, type)
                putString(COUNTRY, country)
                putInt(COUNTRY_ID, countryId)
                putString(GENRE, genre)
                putInt(GENRE_ID, genreId)
                putInt(YEAR1, year1)
                putInt(YEAR2, year2)
                putInt(RATING1, rating1)
                putInt(RATING2, rating2)
                putString(ORDER, order)
            }
            findNavController().navigate(
                R.id.action_filterFragment_to_navigation_search,
                args = bundle
            )
        }

        vmIdCountry.loadIdCountry()
        vmIdGenre.loadIdGenre()



        }

    private fun choiceCountryDialog() {
         var countryArray: Array<String> = arrayOf()
        var countryIdArray: Array<Int> = arrayOf()
        lifecycleScope.launch {
            vmIdCountry.idCountry
                .collect {
                   it.forEach { country ->
                       countryArray = countryArray.plus(country.country)
                       countryIdArray = countryIdArray.plus(country.id)
                   }
                }
        }
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Выберите страну")
            .setItems(countryArray) { _, wich ->
                binding.textDialogCountry.text = countryArray[wich]
                country = countryArray[wich]
                countryId = countryIdArray[wich]
                saveCountry(country)
                saveCountryId(countryId)
            }
        builder.show()
    }

    private fun choiceGenreDialog() {
        var genreArray: Array<String> = arrayOf()
        var genreIdArray: Array<Int> = arrayOf()
        lifecycleScope.launch {
            vmIdGenre.idGenre
                .collect {
                    it.forEach { genre ->
                        genreArray = genreArray.plus(genre.genre)
                        genreIdArray = genreIdArray.plus(genre.id)
                    }
                }
        }
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Выберите жанр")
            .setItems(genreArray) { _, wich ->
                binding.textDialogGenre.text = genreArray[wich]
                genre = genreArray[wich]
                genreId = genreIdArray[wich]
                saveGenre(genre)
                saveGenreId(genreId)
            }
        builder.show()
    }

    private fun saveCountry(country: String) {
        val editor = countryPref?.edit()
        editor?.putString("Country", country)
        editor?.apply()
    }
    private fun saveCountryId(countryId: Int) {
        val editor = countryIdPref?.edit()
        editor?.putInt("CountryId", countryId)
        editor?.apply()
    }

    private fun saveGenre(genre: String) {
        val editor = genrePref?.edit()
        editor?.putString("Genre", genre)
        editor?.apply()
    }
    private fun saveGenreId(genreId: Int) {
        val editor = genreIdPref?.edit()
        editor?.putInt("GenreId", genreId)
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