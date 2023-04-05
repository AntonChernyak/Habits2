package com.antoncherniak.habits.presentation.habitslist.adapter.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.antoncherniak.habits.domain.model.HabitModel

class HabitDiffItemCallback: DiffUtil.ItemCallback<HabitModel>() {

    override fun areItemsTheSame(oldItem: HabitModel, newItem: HabitModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: HabitModel, newItem: HabitModel): Boolean {
        return oldItem == newItem
    }

}