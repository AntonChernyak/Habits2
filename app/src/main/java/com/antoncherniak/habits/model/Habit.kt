package com.antoncherniak.habits.model

import android.graphics.Color

data class Habit(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val priority: PriorityType = PriorityType.HIGH,
    val type: HabitType = HabitType.GOOD_HABIT,
    val periodTimes: String = "",
    val periodDays: String = "",
    val color: Int = Color.WHITE
)