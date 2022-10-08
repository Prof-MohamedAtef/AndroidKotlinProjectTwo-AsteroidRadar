package com.udacity.asteroidradar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidListItemBinding

class AsteroidsRecyclerAdapter(private val clickListener: RecyclerClickListener, private val list: List<Asteroid>?): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    class RecyclerViewHolder (private val binding: AsteroidListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(asteroid: Asteroid) {
            binding.asteroid=asteroid
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecyclerViewHolder(AsteroidListItemBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RecyclerViewHolder).bind(list!![position])
        holder.itemView.setOnClickListener {
            clickListener.onClick(asteroid = list[position])
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    class RecyclerClickListener(private val clickListener:(asteroid:Asteroid)-> Unit){
        fun onClick(asteroid: Asteroid)=clickListener(asteroid)
    }
}