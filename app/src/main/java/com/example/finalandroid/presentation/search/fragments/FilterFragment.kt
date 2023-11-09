package com.example.finalandroid.presentation.search.fragments

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.ColorFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.finalandroid.R
import com.example.finalandroid.databinding.FragmentFilterBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.slider.RangeSlider
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater)
        return binding.root
    }

    private var type = "ALL"
    private var country = "Россия"
    private var genre = "Комедия"
    private var year1 = "1998"
    private var year2 = "2017"
    private var rating1 = 0
    private var rating2 = 0
    private val calendar = Calendar.getInstance()
    private val myFormat = "yyyy"
    private val dateFormat = SimpleDateFormat(myFormat, Locale.US)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
    }

    private val countryArray = arrayOf("Россия", "Великобритания", "США", "Германия", "Франция")
    private fun choiceCountryDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Выберите страну")
            .setItems(countryArray) { dialog, wich ->
                binding.textDialogCountry.text = countryArray[wich]
                country = countryArray[wich]
            }
        builder.show()

    }

    private val genreArray = arrayOf("Комедия", "Мелодрама", "Боевик", "Вестерн", "Драма")
    private fun choiceGenreDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Выберите жанр")
            .setItems(genreArray) { dialog, wich ->
                binding.textDialogGenre.text = genreArray[wich]
                genre = genreArray[wich]
            }
        builder.show()


        binding.rangeSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {}

            override fun onStopTrackingTouch(slider: RangeSlider) {}
        })

        binding.rangeSlider.addOnChangeListener { _, _, _ ->
            val values = binding.rangeSlider.values
            val text = "От ${values[0]} до ${values[1]}"
            binding.textDialogRating.text = text
            rating1 = values[0].toInt()
            rating2 = values[1].toInt()

        }
        val yearArray = arrayOf("1998", "1990", "2000", "2001", "2002", "2003", "2004", "2005", "2006","2007", "2008","2009")
        binding.dialogYear.setOnClickListener {

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Искать в период с")
                .setItems(yearArray) { dialog, wich ->

                    year1 = yearArray[wich]
                }
            val builder1 = AlertDialog.Builder(requireContext())
            builder1.setTitle("Искать в период до")
                .setItems(yearArray) { dialog, wich ->

                    year2 = yearArray[wich]
                }

            builder.show()





//            val dateDialog = MaterialDatePicker.Builder.datePicker()
//                .setTitleText(resources.getString(R.string.dialog_data))
//                .build()
//            dateDialog.addOnPositiveButtonClickListener { timeInMillis ->
//                //calendar.timeInMillis = timeInMillis
//                val year = calendar.get(Calendar.YEAR)
//                val date1 = year
//                binding.textDialogYear.text = date1.toString()
//                Snackbar.make(
//                    binding.dialogYear,
//                    dateFormat.format(calendar.time),
//                    Snackbar.LENGTH_SHORT
//                ).show()
            }
        binding.textDialogYear.text = "C $year1 до $year2"

        //    dateDialog.show(parentFragmentManager, "DatePicker")
       // }
        binding.iconBack.setOnClickListener {
            val bundle = Bundle().apply {
                putString(TYPE, type)
                putString(COUNTRY, country)
                putString(GENRE, genre)
                putString(YEAR1, year1)
                putString(YEAR2, year2)
                putInt(RATING1, rating1)
                putInt(RATING2, rating2)
            }
            findNavController().navigate(
                R.id.action_filterFragment_to_navigation_search,
                args = bundle
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}