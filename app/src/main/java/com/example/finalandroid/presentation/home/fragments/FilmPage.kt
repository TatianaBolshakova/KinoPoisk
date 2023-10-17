package com.example.finalandroid.presentation.home.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.finalandroid.R
import com.example.finalandroid.data.adapters.ActorsAdapter
import com.example.finalandroid.data.adapters.WorkedOnTheFilmAdapter
import com.example.finalandroid.data.adapters.ImagesAdapter
import com.example.finalandroid.data.models.InfoActorsItem
import com.example.finalandroid.data.models.Items
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.adapters.SimilarsAdapter
import com.example.finalandroid.databinding.FragmentFilmPageBinding
import com.example.finalandroid.data.db.App
import com.example.finalandroid.data.db.SelectedFilmsDao
import com.example.finalandroid.data.db.entity.Collections
import com.example.finalandroid.data.db.entity.SelectedFilms
import com.example.finalandroid.presentation.home.viewmodel.ActorsViewModel
import com.example.finalandroid.presentation.home.viewmodel.FilmViewModel
import com.example.finalandroid.presentation.home.viewmodel.ImagesViewModel
import com.example.finalandroid.presentation.home.viewmodel.SimilarsViewModel
import com.example.finalandroid.presentation.profile.fragments.ProfileFragment
import com.example.finalandroid.presentation.profile.viewmodel.AddFilmViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

private const val ID_ACTOR = "actor_id"
private const val ID_FILM = "film_id"
private const val ITEM_IMAGE = "item_image"
private const val NAME_FILM = "name_film"
private const val URL_FILM = "url_film"
private const val NAME_COLLECTION_LIKE = "Like"
private const val COUNT_FILM = "count_film"

class FilmPage(private val profile: ProfileFragment) : Fragment() {

    constructor() : this(ProfileFragment())

    private var _binding: FragmentFilmPageBinding? = null
    private val binding get() = _binding!!
    private val vmFilm: FilmViewModel by viewModels()

    private val vmAddFilm: AddFilmViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dao: SelectedFilmsDao = (activity?.application as App).db.selectedFilmsDao()
                return AddFilmViewModel(dao) as T
            }
        }
    }

    // private val vmFilmInCollection: CollectionSelectedFilmViewModel by viewModels()
    private val vmActors: ActorsViewModel by viewModels()
    private val vmImages: ImagesViewModel by viewModels()
    private val vmSimilars: SimilarsViewModel by viewModels()
    private val actorsAdapter = ActorsAdapter { actors -> onItemClick(actors) }
    private val workedOnTheFilmAdapter = WorkedOnTheFilmAdapter { actors -> onItemClick(actors) }
    private val imagesAdapter = ImagesAdapter { images -> onImageClick(images) }
    private val similarsAdapter = SimilarsAdapter { movie -> onMovieClick(movie) }

    private var isLike: Boolean = false
    private var isIWantToSee: Boolean = false
    var countFilm = 0
    var pref: SharedPreferences? = null
    private var id = 0
    private var nameFilm = ""
    private var urlFilm = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ID_FILM)
            nameFilm = it.getString(NAME_FILM).toString()
            urlFilm = it.getString(URL_FILM).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = this.activity?.getSharedPreferences(
            "COLLECTION",
            Context.MODE_PRIVATE
        )
        val valueLike = pref?.getBoolean(id.toString(), false)!!
        if (isLike != valueLike) {
            binding.like.setImageResource(R.drawable.ic_like_enabled)
        } else {
            binding.like.setImageResource(R.drawable.ic_like_disabled)
        }

        val valueIWantToSee = pref?.getBoolean("I want to see", false)!!
        if (isIWantToSee != valueIWantToSee) {
            binding.iWantToSee.setImageResource(R.drawable.ic_like_enabled)
        } else {
            binding.iWantToSee.setImageResource(R.drawable.i_want_to_see)
        }


        vmFilm.loadInfo(id)
        with(binding) {
            lifecycleScope.launch {
                vmFilm.info
                    .collect {
                        val genreFifst = it?.genres?.first()?.genre
                        var genreLast = it?.genres?.last()?.genre
                        if (genreFifst == genreLast) {
                            genreLast = ""
                        }
                        val countryFifst = it?.countries?.first()?.country
                        var countryLast = it?.countries?.last()?.country
                        if (countryFifst == countryLast) {
                            countryLast = ""
                        }
                        it?.genres?.last()?.genre
                        val textInfo = "" +
                                "${it?.ratingKinopoisk} ${it?.nameRu} \n " +
                                "${it?.year}, $genreFifst, $genreLast \n " +
                                "$countryFifst, $countryLast ${it?.filmLength} min, ${it?.ratingAgeLimits}+"
                        info.text = textInfo

                        description1.text = it?.description
                        description2.text = it?.shortDescription

                        Glide.with(this@FilmPage)
                            .load(it?.coverUrl)
                            .placeholder(R.drawable.plaseholder)
                            .into(imageMovie)
                        Glide.with(this@FilmPage)
                            .load(it?.logoUrl)
                            .into(imageLogo)
                    }
            }
        }
        binding.iconBack.setOnClickListener { findNavController().navigate(R.id.homePage) }

        vmActors.loadInfo(id)



        binding.recyclerActors.adapter = actorsAdapter
        vmActors.actors.onEach { actorsAdapter.setData(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        binding.recyclerWorkedOnTheFilm.adapter = workedOnTheFilmAdapter
        vmActors.actors.onEach { workedOnTheFilmAdapter.setData(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        vmImages.loadInfo(id)
        binding.recyclerImages.adapter = imagesAdapter
        vmImages.images.onEach { imagesAdapter.setData(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        vmSimilars.loadInfo(id)
        binding.recyclerSimilars.adapter = similarsAdapter
        vmSimilars.movie.onEach { similarsAdapter.setData(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)


        binding.apply {

            frameAddLike.setOnClickListener {
                if (isLike == valueLike) {
                    vmAddFilm.addFilm(
                        id =  id.toLong(), nameFilm = nameFilm, urlFilm = urlFilm )
                    binding.like.setImageResource(R.drawable.ic_like_enabled)
                    saveLikeBoolean(true)
                } else {
                    vmAddFilm.deleteFilm(id =  id.toLong(), nameFilm = nameFilm, urlFilm = urlFilm )
                    binding.like.setImageResource(R.drawable.ic_like_disabled)
                    saveLikeBoolean(false)
                }
            }

//            frameAddIWantToSee.setOnClickListener {
//                if (isIWantToSee==valueIWantToSee){
//                    vmAddFilm.addIWantToSeeFilm(id, nameFilm, urlFilm)
//                    binding.iWantToSee.setImageResource(R.drawable.ic_like_enabled)
//                    saveIWantTiSeeBoolean(true)
//                }
//                else
//                {
//                    vmAddFilm.deleteIWantToSeeFilm(id, nameFilm, urlFilm)
//                    binding.iWantToSee.setImageResource(R.drawable.i_want_to_see)
//                    saveIWantTiSeeBoolean(false)
//                }
//
//            }
        }
    }

    private fun saveLikeBoolean(result: Boolean) {
        val editor = pref?.edit()
        editor?.putBoolean(id.toString(), result)
        editor?.apply()
    }

    private fun saveIWantTiSeeBoolean(result: Boolean) {
        val editor = pref?.edit()
        editor?.putBoolean("I want to see", result)
        editor?.apply()
    }


//                val bundle = Bundle().apply {
//                    putString(NAME, vmFilm.info.value?.nameRu)
//                    putInt(ID, vmFilm.info.value?.kinopoiskId?: 0)
//                    putString(URL,vmFilm.info.value?.posterUrl)
//
//                }
//                val name = vmFilm.info.value?.nameRu.toString()
//                val id = vmFilm.info.value?.kinopoiskId?:0
//                val url = vmFilm.info.value?.posterUrl.toString()
//                profile.add(id, name, url)
//                findNavController().navigate(R.id.navigation_profile, args = bundle)


//            frameAddIWantToSee.setOnClickListener { vmViewed.addIWantToSee() }
//            frameAddAlreadyViewed.setOnClickListener { vmViewed.addAlreadyViewed() }
//            frameShare.setOnClickListener { vmViewed.share() }
//            frameOpenAdditionalMenu.setOnClickListener { vmViewed.openAdditionalMenu() }


    private fun onImageClick(item: Items) {
        val bundle = Bundle().apply {
            putString(ITEM_IMAGE, item.imageUrl)
            putInt(ID_FILM, id)
        }
        findNavController().navigate(R.id.imagePage, args = bundle)
    }

    private fun onItemClick(item: InfoActorsItem) {
        val bundle = Bundle().apply {
            putInt(ID_ACTOR, item.staffId)
            putInt(ID_FILM, id)
        }
        findNavController().navigate(R.id.actorPage, args = bundle)
    }

    private fun onMovieClick(item: Movie) {

        val bundle = Bundle().apply {
            putInt(ID_FILM, item.filmId)
        }
        findNavController().navigate(R.id.filmPage, args = bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}