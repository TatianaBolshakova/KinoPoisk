package com.example.finalandroid.presentation.home.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.example.finalandroid.data.db.CollectionsDao
import com.example.finalandroid.data.db.IWantToSeeDao
import com.example.finalandroid.data.db.LikeDao
import com.example.finalandroid.data.db.ViewedDao
import com.example.finalandroid.data.db.WereWonderingDao
import com.example.finalandroid.presentation.home.viewmodel.ActorsViewModel
import com.example.finalandroid.presentation.home.viewmodel.FilmViewModel
import com.example.finalandroid.presentation.home.viewmodel.ImagesViewModel
import com.example.finalandroid.presentation.home.viewmodel.SimilarsViewModel
import com.example.finalandroid.presentation.home.viewmodel.VideoViewModel
import com.example.finalandroid.presentation.profile.viewmodel.AddLikeFilmViewModel
import com.example.finalandroid.presentation.profile.viewmodel.AddIWantToSeeViewModel
import com.example.finalandroid.presentation.profile.viewmodel.AddViewedViewModel
import com.example.finalandroid.presentation.profile.viewmodel.AddWereWonderingViewModel
import com.example.finalandroid.presentation.profile.viewmodel.ListNameCollectionViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

private const val ID_ACTOR = "actor_id"
private const val ID_FILM = "film_id"
private const val ITEM_IMAGE = "item_image"
private const val NAME_FILM = "name_film"
private const val URL_FILM = "url_film"
private const val NAME = "name"
private const val ALL_ACTORS = "Все актеры фильма"
private const val WORKED_ON_THE_FILM = "Над фильмом работали"
private const val NAME_SIMILARS = "Похожие фильмы"


class FilmPage : Fragment() {

    private var _binding: FragmentFilmPageBinding? = null
    private val binding get() = _binding!!
    private val vmFilm: FilmViewModel by viewModels()
    private val vmAddFilm: AddLikeFilmViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val likeDao: LikeDao = (activity?.application as App).db.likeDao()
                val collectionDao: CollectionsDao =
                    (activity?.application as App).db.collectionsDao()
                return AddLikeFilmViewModel(likeDao, collectionDao) as T
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
    private val vmListNameCollection: ListNameCollectionViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dao: CollectionsDao = (activity?.application as App).db.collectionsDao()
                return ListNameCollectionViewModel(dao) as T
            }
        }
    }
    private val vmActors: ActorsViewModel by viewModels()
    private val vmImages: ImagesViewModel by viewModels()
    private val vmSimilars: SimilarsViewModel by viewModels()
    private val vmVideo: VideoViewModel by viewModels()
    private val actorsAdapter = ActorsAdapter { actors -> onItemClick(actors) }
    private val workedOnTheFilmAdapter = WorkedOnTheFilmAdapter { actors -> onItemClick(actors) }
    private val imagesAdapter = ImagesAdapter { images -> onImageClick(images) }
    private val similarsAdapter = SimilarsAdapter { movie -> onMovieClick(movie) }
    private var isLike: Boolean = false
    private var isIWantToSee: Boolean = false
    private var isViewed: Boolean = false
    private var pref: SharedPreferences? = null
    private var id = 0
    private var nameFilm = ""
    private var urlFilm = ""
    private var genre = ""
    private var isWondering = false

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
        val valueWondering = pref?.getBoolean("isWondering $id", false)!!

        vmFilm.loadInfo(id)
        if (isWondering == valueWondering){
            vmWondering.addWereWondering(
                wereWonderingFilmId = id,
                nameFilm = nameFilm,
                urlFilm = urlFilm,
                genre = genre
            )
            saveWonderingBoolean(true)
        }

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
        val valueViewed = pref?.getBoolean("Viewed $id", false)!!
        if (isViewed != valueViewed) {
            binding.addAlreadyViewed.setImageResource(R.drawable.ic_viewed)
        } else {
            binding.addAlreadyViewed.setImageResource(R.drawable.already_viewed)
        }

        with(binding) {
            lifecycleScope.launch {
                vmFilm.info
                    .collect {
                        val textInfo =
                            "${it?.ratingKinopoisk} ${it?.nameRu} \n${it?.year}, ${it?.genres?.joinToString { genre -> genre.genre }}, \n ${it?.countries?.joinToString { country -> country.country }}, ${it?.filmLength} min, ${it?.ratingAgeLimits}+"
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
        binding.iconBack.setOnClickListener {
            findNavController().popBackStack()
        }

        vmActors.loadInfo(id)
        binding.apply {
            recyclerActors.adapter = actorsAdapter
            vmActors.actors.onEach {
                actorsAdapter.setData(it)
                allActors.text = actorsAdapter.itemCount.toString()
            }
                .launchIn(viewLifecycleOwner.lifecycleScope)
            allActors.setOnClickListener {
                val bundle = Bundle().apply {
                    putString(NAME, ALL_ACTORS)
                    putInt(ID_FILM, id)
                }
                findNavController().navigate(R.id.listFilms, args = bundle)
            }
        }

        binding.apply {
            recyclerWorkedOnTheFilm.adapter = workedOnTheFilmAdapter
            vmActors.actors.onEach {
                workedOnTheFilmAdapter.setData(it)
                allWorked.text = it.size.toString()
            }
                .launchIn(viewLifecycleOwner.lifecycleScope)
            allWorked.setOnClickListener {
                val bundle = Bundle().apply {
                    putString(NAME, WORKED_ON_THE_FILM)
                    putInt(ID_FILM, id)
                }
                findNavController().navigate(R.id.listFilms, args = bundle)
            }
        }


        vmImages.loadInfo(id)
        binding.recyclerImages.adapter = imagesAdapter
        vmImages.images.onEach { imagesAdapter.setData(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        vmSimilars.loadInfo(id)
        binding.recyclerSimilars.adapter = similarsAdapter
        vmSimilars.movie.onEach { similarsAdapter.setData(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        vmVideo.loadInfo(id)
        binding.apply {
            imagePlay.setOnClickListener {
                lifecycleScope.launch {
                    vmVideo.info.collect {
                        val urlVideo = it[0].url
                        Toast.makeText(
                            requireContext(),
                            "${urlVideo} -urlVideo ",
                            Toast.LENGTH_SHORT
                        ).show()
                        //  retrofitX.videoPlay(x)

                    }
                }
                vmViewed.addViewed(
                    viewedFilmId = id,
                    nameFilm = nameFilm,
                    urlFilm = urlFilm,
                    genres = genre
                )
                binding.addAlreadyViewed.setImageResource(R.drawable.ic_viewed)
                saveViewedBoolean(true)
            }
            frameAddAlreadyViewed.setOnClickListener {
                vmViewed.addViewed(
                    viewedFilmId = id,
                    nameFilm = nameFilm,
                    urlFilm = urlFilm,
                    genres = genre
                )
                binding.addAlreadyViewed.setImageResource(R.drawable.ic_viewed)
                saveViewedBoolean(true)
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
            frameShare.setOnClickListener {
               // addInCollectionDialog()

                    val intent= Intent()
                    intent.action=Intent.ACTION_SEND
                    intent.putExtra(Intent.EXTRA_TEXT,"Привет, посмотрите этот замечательный фильм:")
                    intent.type="text/plain"
                    startActivity(Intent.createChooser(intent,"Поделиться с:"))

            }
            frameOpenAdditionalMenu.setOnClickListener {
                findNavController().navigate(R.id.addCollectionFragment)
            }
        }
        vmListNameCollection.selectCollection()

        binding.allImage.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(ID_FILM, id)
            }
            findNavController().navigate(R.id.gallery, args = bundle)
        }
        binding.allSimilars.setOnClickListener {
            val bundle = Bundle().apply {
                putString(NAME, NAME_SIMILARS)
                putInt(ID_FILM, id)
            }
            findNavController().navigate(R.id.listFilms, args = bundle)
        }
    }

    private fun saveLikeBoolean(result: Boolean) {
        val editor = pref?.edit()
        editor?.putBoolean("Like $id", result)
        editor?.apply()
    }
    private fun saveWonderingBoolean(result: Boolean) {
        val editor = pref?.edit()
        editor?.putBoolean("isWondering $id", result)
        editor?.apply()
    }
    private fun saveIWantTiSeeBoolean(result: Boolean) {
        val editor = pref?.edit()
        editor?.putBoolean("I want to see $id", result)
        editor?.apply()
    }

    private fun saveViewedBoolean(result: Boolean) {
        val editor = pref?.edit()
        editor?.putBoolean("Viewed $id", result)
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

    private fun addInCollectionDialog() {
        var collections: Array<String> = arrayOf()

        lifecycleScope.launch {
            vmListNameCollection.list
                .collect {
                    collections = it
                }
        }
        val checkedCollections = BooleanArray(collections.size)
        val selectedCollections = mutableListOf(*collections)

        val valueLike = pref?.getBoolean("Like $id", false)!!
        if (isLike != valueLike) {
            checkedCollections[0] = true
        }
        val valueIWantToSee = pref?.getBoolean("I want to see $id", false)!!
        if (isIWantToSee != valueIWantToSee) {
            checkedCollections[1] = true
        }


        val builder = AlertDialog.Builder(requireContext())
        builder
            .setTitle("Выберите коллекцию")
            .setMultiChoiceItems(collections, checkedCollections) { dialog, which, isChecked ->
                checkedCollections[which] = isChecked
                if (isLike == valueLike && which == 0) {
                    vmAddFilm.addFilm(
                        id = id,
                        nameFilm = nameFilm,
                        urlFilm = urlFilm,
                        genre = genre,
                    )
                    binding.like.setImageResource(R.drawable.ic_like_enabled)
                    saveLikeBoolean(true)
                }
                if (isLike != valueLike && which == 0) {
                    vmAddFilm.deleteFilm(
                        id = id,
                        nameFilm = nameFilm,
                        urlFilm = urlFilm,
                        genre = genre
                    )
                    binding.like.setImageResource(R.drawable.ic_like_disabled)
                    saveLikeBoolean(false)
                }
                if (isIWantToSee == valueIWantToSee && which == 1) {
                    vmAddFilm2.addFilm(
                        id,
                        nameFilm,
                        urlFilm,
                        genre
                    )
                    binding.iWantToSee.setImageResource(R.drawable.ic_i_want_to_see_activ)
                    saveLikeBoolean(true)
                }
                if (isIWantToSee != valueIWantToSee && which == 1) {
                    vmAddFilm2.deleteFilm(
                        id,
                        nameFilm,
                        urlFilm,
                        genre
                    )
                    binding.iWantToSee.setImageResource(R.drawable.ic_i_want_to_see)
                    saveLikeBoolean(false)
                }
            }
            .setCancelable(false)
            .setPositiveButton("Сохранить") { dialog, which ->

            }



        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}