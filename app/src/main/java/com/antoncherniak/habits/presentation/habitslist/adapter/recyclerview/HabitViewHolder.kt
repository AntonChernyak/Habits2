package com.antoncherniak.habits.presentation.habitslist.adapter.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.antoncherniak.habits.R
import com.antoncherniak.habits.databinding.ItemHabitBinding
import com.antoncherniak.habits.domain.model.HabitModel

class HabitViewHolder(
    private val binding: ItemHabitBinding,
    private val itemClick: (id: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: HabitModel){
        with(binding) {
            itemTitleTextView. text = model.title
            itemColorView.setBackgroundColor(model.color)
            itemPeriodTextView.text = createPeriodString(model)
            itemPriorityTextView.text = itemView.resources.getString(model.priority.priorityType)
            if (model.description.isNotEmpty()) {
                itemDescriptionTextView.text = model.description
                itemDescriptionTextView.visibility = View.VISIBLE
            } else itemDescriptionTextView.visibility = View.GONE
        }
        itemView.setOnClickListener { itemClick.invoke(model.id) }
    }

    private fun createPeriodString(habit: HabitModel): String {
        return itemView.resources.getString(
            R.string.period_string,
            habit.periodTimes,
            habit.periodDays
        )
    }
}