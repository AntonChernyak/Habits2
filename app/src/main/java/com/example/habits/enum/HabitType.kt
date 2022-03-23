package com.example.habits.enum

import androidx.annotation.StringRes
import com.example.habits.R

enum class HabitType(@StringRes val typeName: Int) {
    GOOD_HABIT(R.string.good_habit),
    BAD_HABIT(R.string.bad_habit);
}