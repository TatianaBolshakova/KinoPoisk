package com.example.finalandroid.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.viewholders.ActorFilmViewHolder
import com.example.finalandroid.databinding.BestFilmItemBinding


class ActorFilmBestAdapter(
    private val onClick: (Movie) -> Unit
) : RecyclerView.Adapter<ActorFilmViewHolder>() {

    private var data: List<Movie> = emptyList()

    fun setData(data: List<Movie>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorFilmViewHolder {
        return ActorFilmViewHolder(
            BestFilmItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ActorFilmViewHolder, position: Int) {
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


