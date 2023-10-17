package com.example.finalandroid.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalandroid.R

import com.example.finalandroid.data.viewholders.CollectionViewHolder
import com.example.finalandroid.databinding.CollectionsItemBinding
private const val NAME_COLLECTION_LIKE = "Любимые"
private const val NAME_COLLECTION_I_WANT_TO_SEE = "Хочу посмотреть"
class DefaultCollectionAdapter(
    private val onClick: (CollectionsItemBinding) -> Unit
) : RecyclerView.Adapter<CollectionViewHolder>() {

    private var data: List<CollectionsItemBinding?> = emptyList()

    fun setData(data: List<CollectionsItemBinding>) {
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
            when (position) {
                0 -> {
                    textNameCollection.text = NAME_COLLECTION_LIKE
                    textCount.text = 0.toString()
                    item?.let {
                        Glide
                            .with(image.context)
                            .load(R.drawable.ic_like_disabled)
                            .into(image)
                    }

                }

                1 -> {
                    textNameCollection.text = NAME_COLLECTION_I_WANT_TO_SEE
                    textCount.text =0.toString()
                    item?.let {
                        Glide
                            .with(image.context)
                            .load(R.drawable.i_want_to_see)
                            .into(image)
                    }

                }


            }
            holder.binding.root.setOnClickListener {
                item?.let {
                    onClick(item)
                }
            }
        }
    }
    override fun getItemCount(): Int = data.size
}