package com.example.finalandroid.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.finalandroid.data.models.Items
import com.example.finalandroid.data.viewholders.ImagesViewHolder
import com.example.finalandroid.databinding.ImagesItemBinding

class ImagesAdapter(
    private val onClick: (Items) -> Unit
) : RecyclerView.Adapter<ImagesViewHolder>() {

    private var data: List<Items>? = emptyList()

    fun setData(data: List<Items>?) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        return ImagesViewHolder(
            ImagesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        val item = data?.getOrNull(position)
        with(holder.binding) {
            imageView.isClickable= false
            item?.let {
                Glide
                    .with(imageView.context)
                    .load(it.imageUrl)
                    //.apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                    .into(imageView)

            }
        }
        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(item)
            }
        }
    }

    override fun getItemCount(): Int = data!!.size
}


