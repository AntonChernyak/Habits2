package com.example.habits.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.habits.data.database.model_vo.HabitItem

class HabitDiffCallback(
    private val oldList: List<HabitItem>,
    private val newList: List<HabitItem>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}