package com.example.finalandroid.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalandroid.data.db.entity.SelectedFilms

import com.example.finalandroid.data.db.entity.SelectedFilmsWithCollections
import com.example.finalandroid.data.viewholders.MovieViewHolder
import com.example.finalandroid.databinding.MovieItemBinding


class LikeFilmAdapter(
    private val onClick: (SelectedFilms) -> Unit
) : RecyclerView.Adapter<MovieViewHolder>() {

    private var data: List<SelectedFilms?> = emptyList()

    fun setData(data: List<SelectedFilms>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = data.getOrNull(position)



        with(holder.binding) {

            titleText.text = item?.nameFilm

            item?.let {
                Glide
                    .with(imageView.context)
                   .load(it.urlFilm)
                   // .placeholder(R.drawable.image_plaseholder)
                    .into(imageView)
            }
        }
        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(item)
            }
        }
    }

    override fun getItemCount(): Int = data.size
}

