package com.antoncherniak.habits.model

import androidx.annotation.StringRes
import com.antoncherniak.habits.R

enum class PriorityType(@StringRes val priorityType: Int, val spinnerPos: Int = 0) {
    HIGH(R.string.high_priority, 0),
    MEDIUM(R.string.medium_priority, 1),
    LOW(R.string.low_priority, 2)
}