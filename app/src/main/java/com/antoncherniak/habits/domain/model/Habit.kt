package com.antoncherniak.habits.domain.model

import android.graphics.Color
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Habit(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val priority: PriorityType = PriorityType.HIGH,
    val type: HabitType = HabitType.GOOD_HABIT,
    val periodTimes: String = "",
    val periodDays: String = "",
    val color: Int = Color.WHITE
):Parcelable