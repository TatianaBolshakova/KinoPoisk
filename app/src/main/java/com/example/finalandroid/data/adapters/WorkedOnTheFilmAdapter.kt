package com.example.finalandroid.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.finalandroid.R
import com.example.finalandroid.data.models.InfoActorsItem
import com.example.finalandroid.databinding.ActorsItemBinding
import com.example.finalandroid.data.viewholders.ActorsViewHolder


class WorkedOnTheFilmAdapter(
    private val onClick: (InfoActorsItem) -> Unit
) : RecyclerView.Adapter<ActorsViewHolder>() {

    private var data: List<InfoActorsItem> = emptyList()

    fun setData(data: List<InfoActorsItem>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsViewHolder {
        return ActorsViewHolder(
            ActorsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ActorsViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {
                nameText.text = item?.nameRu ?: ""
                descriptionText.text = item?.professionText ?: ""
                imageView.isClickable= false
                item?.let {
                    Glide

                        .with(imageView.context)
                        .load(it.posterUrl)
                        .placeholder(R.drawable.ic_man)
                        .into(imageView)

                }
        }
        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(item)
            }
        }
    }

    override fun getItemCount(): Int{
        data= data.filter { infoActorsItem ->
            infoActorsItem.professionKey!="ACTOR"
        }
        return data.size
    }
}
