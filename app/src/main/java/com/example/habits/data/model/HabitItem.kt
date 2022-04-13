package com.example.habits.data.model

import android.graphics.Color
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class HabitItem(
    val id: Int = -1,
    val title: String,
    val description: String = "",
    val priority: String,
    val type: HabitType = HabitType.GOOD_HABIT,
    val periodCount: String,
    val periodDays: String,
    val color: Int = Color.WHITE,
    val dateOfCreation: Long = Date().time,
    var isChecked: Boolean = false
): Parcelable