package com.example.finalandroid.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.viewholders.ActorFilmViewHolder
import com.example.finalandroid.data.viewholders.FilmographyViewHolder
import com.example.finalandroid.databinding.BestFilmItemBinding
import com.example.finalandroid.databinding.FilmographyItemBinding


class ActorFilmBestListAdapter(
    private val onClick: (Movie) -> Unit
) : RecyclerView.Adapter<FilmographyViewHolder>() {

    private var data: List<Movie> = emptyList()

    fun setData(data: List<Movie>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmographyViewHolder {
        return FilmographyViewHolder(
            FilmographyItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FilmographyViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {
           // if (item?.rating!=null&& item.rating >= 7.0){
                titleText.text = item?.nameRu?:""
                textRating.text = item?.rating?.toString()?:""
            //genreText.text = item.genres.joinToString { it.genre }?:"Жанр не указан"
           // }
        }
        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(item)
            }
        }
    }

    override fun getItemCount(): Int{

        data= data.filter {movie->
            movie.rating >= 7.0
        }
        return data.size
    }

}


