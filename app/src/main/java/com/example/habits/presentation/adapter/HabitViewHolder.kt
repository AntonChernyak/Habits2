package com.example.habits.presentation.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.habits.R
import com.example.habits.data.model_vo.HabitItem

class HabitViewHolder(
    itemView: View,
    val itemListener: (position: Int) -> Unit,
    val checkListener: (v: View, pos: Int) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val title = itemView.findViewById<TextView>(R.id.habitTitleTextView)
    private val description = itemView.findViewById<TextView>(R.id.habitDescriptionTextView)
    private val habitColorLabel = itemView.findViewById<View>(R.id.habitColorLabel)
    private val period = itemView.findViewById<TextView>(R.id.periodTextView)
    private val priority = itemView.findViewById<TextView>(R.id.priorityTextView)
    private val type = itemView.findViewById<TextView>(R.id.typeTextView)
    private val checkImageView = itemView.findViewById<ImageView>(R.id.checkImageView)

    init {
        itemView.setOnClickListener { itemListener(bindingAdapterPosition) }
        checkImageView.setOnClickListener { checkListener(checkImageView, bindingAdapterPosition) }
    }

    fun bind(habit: HabitItem) {
        title.text = habit.title
        habitColorLabel.setBackgroundColor(habit.color)
        period.text = createPeriodString(habit)
        priority.text = habit.priority
        type.text = itemView.resources.getString(habit.type.typeName)

        if (habit.description.isNotEmpty()) {
            description.text = habit.description
            description.visibility = View.VISIBLE
        } else description.visibility = View.GONE

        checkImageView.isSelected = habit.isChecked
    }

    private fun createPeriodString(habit: HabitItem): String {
        return itemView.resources.getString(
            R.string.period_string,
            habit.periodCount,
            habit.periodDays
        )
    }
}