package com.example.habits.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habits.R
import com.example.habits.model.HabitItem

class HabitAdapter(
    private val itemClick: (position: Int) -> Unit,
    private val checkClick: (view: View, position: Int) -> Unit
): RecyclerView.Adapter<HabitViewHolder>() {

    var data: MutableList<HabitItem> = mutableListOf()
    @SuppressLint("NotifyDataSetChanged")
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_habit, parent, false)
        return HabitViewHolder(view, itemClick, checkClick)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = data[position]
        holder.bind(habit)
    }

    fun removeAt(position: Int){
        data.removeAt(position)
        notifyItemRemoved(position)
    }

}