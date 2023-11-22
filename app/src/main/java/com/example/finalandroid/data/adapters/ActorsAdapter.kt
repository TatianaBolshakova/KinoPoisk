package com.example.finalandroid.data.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalandroid.R
import com.example.finalandroid.data.models.InfoActorsItem
import com.example.finalandroid.databinding.ActorsItemBinding
import com.example.finalandroid.data.viewholders.ActorsViewHolder

class ActorsAdapter(
    private val onClick: (InfoActorsItem) -> Unit
) : RecyclerView.Adapter<ActorsViewHolder>() {

    private var data: List<InfoActorsItem> = emptyList()
    private var dataActor: List<InfoActorsItem> = emptyList()
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
            nameText.text = item?.nameRu
            descriptionText.text = item?.description
            imageView.isClickable= false
            item.let {
                Glide
                    .with(imageView.context)
                    .load(it?.posterUrl)
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
        data.toMutableList()
        dataActor= data.filter { infoActorsItem ->
            infoActorsItem.professionKey=="ACTOR"
        }
        return dataActor.size
    }
}


