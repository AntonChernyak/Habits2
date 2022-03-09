package com.example.habits.model

import android.graphics.Color
import com.example.habits.enum.HabitType

data class HabitItem(
    val title: String,
    val description: String = "",
    val priority: String,
    val type: HabitType = HabitType.GOOD_HABIT,
    val periodCount: String,
    val periodDays: String,
    val color: Int = Color.WHITE
)