package com.example.finalandroid.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.databinding.MovieItemBinding


class SimilarsAdapter(
    private val onClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieViewHolder>() {

    private var data: List<Movie> = emptyList()

    fun setData(data: List<Movie>?) {
        this.data = data!!
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
            titleText.text = item?.nameRu ?: ""
            genresText.text = item?.genres?.get(0)?.genre ?: ""
            layout.bringToFront()
            imageView.isClickable= false
            item?.let {
                Glide
                    .with(imageView.context)
                    .load(it.posterUrlPreview)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
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

