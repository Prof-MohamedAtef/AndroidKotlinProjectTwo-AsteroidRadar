package com.udacity.asteroidradar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R

class AsteroidsRecyclerAdapter (private val context: Context, private val list: List<Asteroid>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvCodeName: TextView
        var tvDate: TextView

        init {
            tvCodeName = itemView.findViewById(R.id.tvCodeName)
            tvDate = itemView.findViewById(R.id.tvDate)
        }

        fun bind(position: Int) {
            tvCodeName.text = list[position].codename
            tvDate.text = list[position].closeApproachDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.asteroid_list_item, parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}