package com.example.finalandroid.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalandroid.data.models.Movie
import com.example.finalandroid.data.viewholders.HomePageViewHolder
import com.example.finalandroid.databinding.FragmentHomePageBinding
import kotlin.random.Random
import kotlin.random.nextInt


class HomePageAdapter(
   // private val onClick: (Movie) -> Unit
) : RecyclerView.Adapter<HomePageViewHolder>() {

    private var data: List<Movie> = emptyList()

    fun setData(data: List<Movie>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePageViewHolder {
        return HomePageViewHolder(
            FragmentHomePageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    private var type = when(Random.nextInt(1 until 5)){
        1->"FILM"
        2->"TV_SHOW"
        3->"TV_SERIES"
        4->"MINI_SERIES"
        else->"ALL"
    }
    override fun onBindViewHolder(holder: HomePageViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {

        }
//        holder.binding.root.setOnClickListener {
//            item?.let {
//                onClick(item)
//            }
//        }
    }

    override fun getItemCount(): Int = data.size
}


