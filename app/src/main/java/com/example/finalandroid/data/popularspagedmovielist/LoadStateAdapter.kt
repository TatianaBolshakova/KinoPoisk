package com.example.finalandroid.data.popularspagedmovielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalandroid.databinding.LoadStateBinding


class MyLoadStateAdapter : LoadStateAdapter<LoadStateWiewHolder>() {
    override fun onBindViewHolder(holder: LoadStateWiewHolder, loadState: LoadState)  = Unit
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateWiewHolder {
       val binding = LoadStateBinding.inflate(LayoutInflater.from(parent.context),parent, false )
    return LoadStateWiewHolder(binding)
    }
}
class LoadStateWiewHolder(binding: LoadStateBinding): RecyclerView.ViewHolder(binding.root)