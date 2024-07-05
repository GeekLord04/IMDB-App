package com.geeklord.imdbapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.geeklord.imdbapp.Models.Search
import com.geeklord.imdbapp.databinding.LayoutItemBinding

class ItemAdapter (private val onClick : (Search) -> Unit) : ListAdapter<Search, ItemAdapter.ItemViewHolder>(ComparatorDiffUtil())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = LayoutItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        item.let {
            holder.bind(it)
        }
    }

    inner class ItemViewHolder(private val binding: LayoutItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(data : Search){
            binding.name.text = data.Title
            binding.description.text = data.Type
            binding.poster.load(data.Poster){
                transformations(RoundedCornersTransformation(16f)) // Set the radius of rounded corners
                size(100,100)
                placeholder(R.drawable.ic_launcher_background) // Placeholder image while loading
                error(R.drawable.ic_launcher_foreground) // Error image if loading fails
            }

            binding.root.setOnClickListener{
                onClick(data)
            }
        }

    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<Search>(){
        override fun areItemsTheSame(oldItem: Search, newItem: Search): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Search, newItem: Search): Boolean {
            return oldItem == newItem
        }

    }
}