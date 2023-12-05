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
import com.example.finalandroid.data.constsnts.Constants
import com.example.finalandroid.databinding.FragmentFilterBinding
import com.example.finalandroid.presentation.search.viewmodel.IdCountryViewModel
import com.example.finalandroid.presentation.search.viewmodel.IdGenreViewModel
import com.google.android.material.slider.RangeSlider
import kotlinx.coroutines.launch

class FilterFragment : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private var type = "ALL"
    private var country = Constants.DEF_VALUE_COUNTRY
    private var countryId = 1
    private var genre = "Комедия"
    private var genreId =1
    private var year1 = 2003
    private var year2 = 2023
    private var rating1 = 0
    private var rating2 = 10
    private var order = "RATING"
    private var pref: SharedPreferences? = null
    private var prefInt: SharedPreferences? = null
    private var prefYear: SharedPreferences? = null
    private var year1Pref: SharedPreferences? = null
    private var year2Pref: SharedPreferences? = null
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


        pref = this.activity?.getSharedPreferences(Constants.NAME_PREF_FILTER, Context.MODE_PRIVATE)
        prefInt = this.activity?.getSharedPreferences(Constants.NAME_PREF_FILTER_1, Context.MODE_PRIVATE)


        country = pref?.getString(Constants.KEY_COUNTRY, Constants.DEF_VALUE_COUNTRY)!!
        binding.textDialogCountry.text = country

        countryId = prefInt?.getInt(Constants.KEY_COUNTRY_ID, 1)!!

        genre = pref?.getString(Constants.KEY_GENRE, Constants.DEF_VALUE_GENRE)!!
        binding.textDialogGenre.text = genre

        genreId = prefInt?.getInt(Constants.KEY_GENRE_ID, 1)!!

        prefYear = this.activity?.getSharedPreferences(Constants.NAME_PREF_YEAR, Context.MODE_PRIVATE)
        year1 = prefYear?.getInt(Constants.KEY_YEAR_1, 1998)!!
        year2 = prefYear?.getInt(Constants.KEY_YEAR_2, 2023)!!
        if (year1 < year2) {
            textDialogYear = " С $year1 до $year2"
        } else {
            textDialogYear = " С $year1 до $year1"
            year2=year1
        }
        binding.textDialogYear.text = textDialogYear

        rating1 = prefInt?.getInt(Constants.KEY_RATING_1, 0)!!
        rating2 = prefInt?.getInt(Constants.KEY_RATING_2, 10)!!
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
                putString(Constants.TYPE, type)
                putString(Constants.COUNTRY, country)
                putInt(Constants.COUNTRY_ID, countryId)
                putString(Constants.GENRE, genre)
                putInt(Constants.GENRE_ID, genreId)
                putInt(Constants.YEAR1, year1)
                putInt(Constants.YEAR2, year2)
                putInt(Constants.RATING1, rating1)
                putInt(Constants.RATING2, rating2)
                putString(Constants.ORDER, order)
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
        val editor = pref?.edit()
        editor?.putString(Constants.KEY_COUNTRY, country)

        editor?.apply()
    }
    private fun saveCountryId(countryId: Int) {
        val editor = prefInt?.edit()
        editor?.putInt(Constants.KEY_COUNTRY_ID, countryId)
        editor?.apply()
    }

    private fun saveGenre(genre: String) {
        val editor = pref?.edit()
        editor?.putString(Constants.KEY_GENRE, genre)
        editor?.apply()
    }
    private fun saveGenreId(genreId: Int) {
        val editor = prefInt?.edit()
        editor?.putInt(Constants.KEY_GENRE_ID, genreId)
        editor?.apply()
    }

    private fun saveRating1(rating: Int) {
        val editor = prefInt?.edit()
        editor?.putInt(Constants.KEY_RATING_1, rating)
        editor?.apply()
    }

    private fun saveRating2(rating: Int) {
        val editor = prefInt?.edit()
        editor?.putInt(Constants.KEY_RATING_2, rating)
        editor?.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}