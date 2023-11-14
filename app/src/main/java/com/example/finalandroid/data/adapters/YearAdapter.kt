package com.example.finalandroid.data.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalandroid.data.viewholders.YearHolder
import com.example.finalandroid.databinding.MyDialogItemBinding


class YearAdapter(
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<YearHolder>() {
    var data: List<Int> = List(1000) { (it + 1970) + 1 }
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
        }
    }

    override fun getItemCount(): Int = data.size
}

