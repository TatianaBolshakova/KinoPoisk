package com.example.finalandroid.presentation.search.fragments


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalandroid.databinding.MyDialogItemBinding


class YearAdapter(
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<YearHolder>() {
    var data: List<Int> = List(1000) { (it + 1950) + 1 }

    fun setData() {

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YearHolder {
        return YearHolder(
            MyDialogItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: YearHolder, position: Int) {
        val item = data.getOrNull(position)
        holder.binding.textView.text = item.toString()
        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(item)

            }
//            if (NoClick){
//                holder.binding.textView.setBackgroundColor(Color.BLUE)
//            }else{
//                holder.binding.textView.setBackgroundColor(Color.WHITE)
//            }
        }

    }

    override fun getItemCount(): Int = data.size
}

class YearHolder(val binding: MyDialogItemBinding) : RecyclerView.ViewHolder(binding.root)