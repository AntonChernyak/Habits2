package com.antoncherniak.habits.model

import androidx.annotation.StringRes
import com.antoncherniak.habits.R

enum class HabitType(@StringRes val typeName: Int) {
    GOOD_HABIT(R.string.good_habit),
    BAD_HABIT(R.string.bad_habit)
}