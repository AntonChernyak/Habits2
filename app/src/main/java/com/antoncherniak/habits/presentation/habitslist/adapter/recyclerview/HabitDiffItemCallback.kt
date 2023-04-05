package com.antoncherniak.habits.presentation.habitslist.adapter.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.antoncherniak.habits.domain.model.Habit

class HabitDiffItemCallback: DiffUtil.ItemCallback<Habit>() {

    override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
        return oldItem == newItem
    }

}