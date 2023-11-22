package com.example.finalandroid.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalandroid.data.models.InfoActorsItem
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.viewholders.BestFilmViewHolder
import com.example.finalandroid.databinding.BestFilmItemBinding


class ActorFilmBestAdapter(
    private val onClick: (Movie) -> Unit
) : RecyclerView.Adapter<BestFilmViewHolder>() {

    private var data: List<Movie> = emptyList()
    private var dataBest: List<Movie> = emptyList()
    fun setData(data: List<Movie>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestFilmViewHolder {
        return BestFilmViewHolder(
            BestFilmItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BestFilmViewHolder, position: Int) {
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
        data.toMutableList()
        dataBest= data.filter {movie->
            movie.rating.toString() == "8.0"
        }
        return dataBest.size
    }

}


