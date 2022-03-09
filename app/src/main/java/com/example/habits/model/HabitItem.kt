package com.example.habits.model

import com.example.habits.enum.HabitType

data class HabitItem(
    val title: String,
    val description: String,
    val priority: String,
    val type: HabitType,
    val periodCount: String,
    val periodDays: String,
    val color: Int
)