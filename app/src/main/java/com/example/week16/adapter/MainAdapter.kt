package com.example.week16.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week16.databinding.ItemListBinding
import com.example.week16.model.Posts

class MainAdapter(private var posts: List<Posts>, val listener: onAdapterClick): RecyclerView.Adapter<MainAdapter.PostViewHolder>() {

    inner class PostViewHolder(val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root)

    interface onAdapterClick{
        fun onClick(posts: Posts)
        fun onUpdate(posts: Posts)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(ItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.binding.apply {
            tvPlaceHolder.text = posts[position].first_name
            tvPlaceHolder.setOnClickListener {
                listener.onClick(posts[position])
            }
            iconUpdate.setOnClickListener {
                listener.onUpdate(posts[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}