package com.example.habits.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habits.R
import com.example.habits.model.HabitItem

class HabitAdapter(): RecyclerView.Adapter<HabitViewHolder>() {

    var data: List<HabitItem> = emptyList()
        set(value) {
            field = value
            this.notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_habit, parent, false)
        return HabitViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = data[position]
        holder.bind(habit)
    }

}