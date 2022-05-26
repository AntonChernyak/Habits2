package com.example.habits.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.habits.R
import com.example.habits.data.model_vo.HabitItem

class HabitAdapter(
    private val itemClick: (position: Int) -> Unit,
    private val checkClick: (view: View, position: Int) -> Unit
) : RecyclerView.Adapter<HabitViewHolder>() {

    var data: List<HabitItem> = emptyList()
        set(newValue) {
            val diffCallback = HabitDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
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
}