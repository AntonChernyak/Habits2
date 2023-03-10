package com.antoncherniak.habits.model

import androidx.annotation.StringRes
import com.antoncherniak.habits.R

enum class PriorityType(@StringRes val priorityType: Int) {
    HIGH(R.string.high_priority),
    MEDIUM(R.string.medium_priority),
    LOW(R.string.low_priority)
}