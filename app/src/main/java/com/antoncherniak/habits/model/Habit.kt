package com.antoncherniak.habits.model

import android.graphics.Color

class Habit(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val priority: PriorityType = PriorityType.HIGH,
    val type: HabitType = HabitType.GOOD_HABIT,
    val periodTimes: Int = 0,
    val periodDays: Int = 0,
    val color: Int = Color.WHITE
)