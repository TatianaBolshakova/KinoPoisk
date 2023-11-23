package com.example.finalandroid.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.viewholders.FilmographyViewHolder
import com.example.finalandroid.databinding.FilmographyItemBinding


class FilmographyAdapter(
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
            titleText.text = item?.nameRu?:"Название не указано"
            textRating.text = item?.rating.toString()

        }
        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(item)
            }
        }
    }

    override fun getItemCount(): Int = data.size
}


