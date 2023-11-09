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
import com.example.finalandroid.data.db.IWantToSeeDao
import com.example.finalandroid.data.db.LikeDao
import com.example.finalandroid.data.db.ViewedDao
import com.example.finalandroid.data.db.WereWonderingDao
import com.example.finalandroid.presentation.home.viewmodel.ActorsViewModel
import com.example.finalandroid.presentation.home.viewmodel.FilmViewModel
import com.example.finalandroid.presentation.home.viewmodel.ImagesViewModel
import com.example.finalandroid.presentation.home.viewmodel.SimilarsViewModel
import com.example.finalandroid.presentation.profile.viewmodel.AddLikeFilmViewModel
import com.example.finalandroid.presentation.profile.viewmodel.AddIWantToSeeViewModel
import com.example.finalandroid.presentation.profile.viewmodel.AddViewedViewModel
import com.example.finalandroid.presentation.profile.viewmodel.AddWereWonderingViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

private const val ID_ACTOR = "actor_id"
private const val ID_FILM = "film_id"
private const val ITEM_IMAGE = "item_image"
private const val NAME_FILM = "name_film"
private const val URL_FILM = "url_film"
private const val GENRE = "genre"
class FilmPage : Fragment() {

    private var _binding: FragmentFilmPageBinding? = null
    private val binding get() = _binding!!
    private val vmFilm: FilmViewModel by viewModels()
    private val vmAddFilm: AddLikeFilmViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dao: LikeDao = (activity?.application as App).db.likeDao()
                return AddLikeFilmViewModel(dao) as T
            }
        }
    }
    private val vmAddFilm2: AddIWantToSeeViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dao: IWantToSeeDao = (activity?.application as App).db.iWantToSeeDao()
                return AddIWantToSeeViewModel(dao) as T
            }
        }
    }
    private val vmViewed: AddViewedViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dao: ViewedDao = (activity?.application as App).db.viewedDao()
                return AddViewedViewModel(dao) as T
            }
        }
    }
    private val vmWondering: AddWereWonderingViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dao: WereWonderingDao = (activity?.application as App).db.wereWonderingDao()
                return AddWereWonderingViewModel(dao) as T
            }
        }
    }

    private val vmActors: ActorsViewModel by viewModels()
    private val vmImages: ImagesViewModel by viewModels()
    private val vmSimilars: SimilarsViewModel by viewModels()
    private val actorsAdapter = ActorsAdapter { actors -> onItemClick(actors) }
    private val workedOnTheFilmAdapter = WorkedOnTheFilmAdapter { actors -> onItemClick(actors) }
    private val imagesAdapter = ImagesAdapter { images -> onImageClick(images) }
    private val similarsAdapter = SimilarsAdapter { movie -> onMovieClick(movie) }

    private var isLike: Boolean = false
    private var isIWantToSee: Boolean = false
    private var pref: SharedPreferences? = null
    private var id = 0
    private var nameFilm = ""
    private var urlFilm = ""
    private var genre =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ID_FILM)
            nameFilm = it.getString(NAME_FILM).toString()
            urlFilm = it.getString(URL_FILM).toString()
            genre = it.getString(GENRE).toString()
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

        vmWondering.addWereWondering(
            wereWonderingFilmId = id,
            nameFilm = nameFilm,
            urlFilm = urlFilm,
            genre = genre
        )
        pref = this.activity?.getSharedPreferences(
            "COLLECTION",
            Context.MODE_PRIVATE
        )
        val valueLike = pref?.getBoolean("Like $id", false)!!
        if (isLike != valueLike) {
            binding.like.setImageResource(R.drawable.ic_like_enabled)
        } else {
            binding.like.setImageResource(R.drawable.ic_like_disabled)
        }

        val valueIWantToSee = pref?.getBoolean("I want to see $id", false)!!
        if (isIWantToSee != valueIWantToSee) {
            binding.iWantToSee.setImageResource(R.drawable.ic_i_want_to_see_activ)
        } else {
            binding.iWantToSee.setImageResource(R.drawable.ic_i_want_to_see)
        }


        vmFilm.loadInfo(id)
        with(binding) {
            lifecycleScope.launch {
                vmFilm.info
                    .collect {
                        val genreFirst = it?.genres?.first()?.genre
                        var genreLast = it?.genres?.last()?.genre
                        if (genreFirst == genreLast) {
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
                                "${it?.year}, $genreFirst, $genreLast \n " +
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
            imagePlay.setOnClickListener {
                vmViewed.addViewed(viewedFilmId = id, nameFilm = nameFilm, urlFilm = urlFilm, genre = genre)
            }
            frameAddLike.setOnClickListener {
                if (isLike == valueLike) {
                    vmAddFilm.addFilm(
                        id = id,
                        nameFilm = nameFilm,
                        urlFilm = urlFilm,
                        genre = genre,
                    )
                    binding.like.setImageResource(R.drawable.ic_like_enabled)
                    saveLikeBoolean(true)

                } else {
                    vmAddFilm.deleteFilm(
                        id = id,
                        nameFilm = nameFilm,
                        urlFilm = urlFilm,
                        genre = genre
                    )
                    binding.like.setImageResource(R.drawable.ic_like_disabled)
                    saveLikeBoolean(false)
                }
            }

            frameAddIWantToSee.setOnClickListener {
                if (isIWantToSee == valueIWantToSee) {
                    vmAddFilm2.addFilm(
                        id,
                        nameFilm,
                        urlFilm,
                        genre
                    )
                    binding.iWantToSee.setImageResource(R.drawable.ic_i_want_to_see_activ)
                    saveIWantTiSeeBoolean(true)
                } else {
                    vmAddFilm2.deleteFilm(
                        id,
                        nameFilm,
                        urlFilm,
                        genre
                    )
                    binding.iWantToSee.setImageResource(R.drawable.ic_i_want_to_see)
                    saveIWantTiSeeBoolean(false)
                }

            }
        }
    }

    private fun saveLikeBoolean(result: Boolean) {
        val editor = pref?.edit()
        editor?.putBoolean("Like $id", result)
        editor?.apply()
    }

    private fun saveIWantTiSeeBoolean(result: Boolean) {
        val editor = pref?.edit()
        editor?.putBoolean("I want to see $id", result)
        editor?.apply()
    }

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