package com.antoncherniak.habits.presentation.habitslist.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.antoncherniak.habits.databinding.ItemHabitBinding
import com.antoncherniak.habits.domain.model.HabitModel

class HabitListAdapter(
    private val openHabitItemClick: (habitId: Int) -> Unit
) : ListAdapter<HabitModel, HabitViewHolder>(HabitDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder =
        HabitViewHolder(
            ItemHabitBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            openHabitItemClick
        )

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = currentList.size

    override fun getItemId(position: Int): Long = position.toLong()
}