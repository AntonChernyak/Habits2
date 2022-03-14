package com.example.habits.model

import android.graphics.Color
import android.os.Parcelable
import com.example.habits.enum.HabitType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HabitItem(
    val title: String,
    val description: String = "",
    val priority: String,
    val type: HabitType = HabitType.GOOD_HABIT,
    val periodCount: String,
    val periodDays: String,
    val color: Int = Color.WHITE
): Parcelable