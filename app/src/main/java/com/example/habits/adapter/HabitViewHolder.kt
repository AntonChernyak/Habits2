package com.example.habits.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.habits.R
import com.example.habits.enum.HabitType
import com.example.habits.model.HabitItem

class HabitViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val title = itemView.findViewById<TextView>(R.id.habitTitleTextView)
    private val description = itemView.findViewById<TextView>(R.id.habitDescriptionTextView)
    private val habitColorLabel = itemView.findViewById<View>(R.id.habitColorLabel)
    private val period = itemView.findViewById<TextView>(R.id.periodTextView)
    private val priority = itemView.findViewById<TextView>(R.id.priorityTextView)
    private val type = itemView.findViewById<TextView>(R.id.typeTextView)

    fun bind(habit: HabitItem) {
        title.text = habit.title
        habitColorLabel.setBackgroundColor(habit.color)
        period.text = createPeriodString(habit)
        priority.text = habit.priority
        type.text = convertTypeToString(habit.type)

        if (habit.description.isNotEmpty()) {
            description.text = habit.description
        } else description.visibility = View.GONE
    }

    private fun createPeriodString(habit: HabitItem): String{
        return itemView.resources.getString(R.string.period_string, habit.periodCount, habit.periodDays)
    }

    private fun convertTypeToString(type: HabitType): String{
        return if (type == HabitType.GOOD_HABIT) {
            itemView.resources.getString(R.string.good_habit)
        } else {
            itemView.resources.getString(R.string.bad_habit)
        }
    }
}