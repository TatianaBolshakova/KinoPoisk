package com.example.finalandroid.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.finalandroid.R
import com.example.finalandroid.data.db.entity.WereWondering
import com.example.finalandroid.data.viewholders.MovieViewHolder
import com.example.finalandroid.databinding.MovieItemBinding

class WereWonderingFilmsAdapter(
    private val onClick: (WereWondering) -> Unit
) : RecyclerView.Adapter<MovieViewHolder>() {

    private var data: List<WereWondering?> = emptyList()

    fun setData(data: List<WereWondering>) {
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
            genresText.text = item?.genre

            item?.let {
                Glide.with(imageView.context)
                    .load(item.urlFilm)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                    .placeholder(R.drawable.my_collection)
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