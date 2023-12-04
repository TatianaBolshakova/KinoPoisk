package com.example.finalandroid.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalandroid.R
import com.example.finalandroid.data.db.entity.Collections
import com.example.finalandroid.data.viewholders.CollectionViewHolder
import com.example.finalandroid.databinding.CollectionsItemBinding

class CollectionAdapter(
    private val onClick: (Collections) -> Unit
) : RecyclerView.Adapter<CollectionViewHolder>() {

    private var data: List<Collections?> = emptyList()

    fun setData(data: List<Collections>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        return CollectionViewHolder(
            CollectionsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val item = data.getOrNull(position)

        with(holder.binding) {

                    textNameCollection.text = item?.collectionsName
                    item?.let {
                        Glide
                            .with(image.context)
                            .load(item.collectionsIcon)
                            .placeholder(R.drawable.my_collection)
                            .into(image)
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

