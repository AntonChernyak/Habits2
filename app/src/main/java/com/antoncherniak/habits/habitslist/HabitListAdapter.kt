package com.antoncherniak.habits.habitslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.antoncherniak.habits.databinding.ItemHabitBinding
import com.antoncherniak.habits.model.Habit

class HabitListAdapter : ListAdapter<Habit, HabitViewHolder>(HabitDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder =
        HabitViewHolder(ItemHabitBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int = currentList.size

    override fun getItemId(position: Int): Long = position.toLong()
}