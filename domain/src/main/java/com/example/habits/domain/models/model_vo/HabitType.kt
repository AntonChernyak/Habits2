package com.example.habits.domain.models.model_vo

import android.os.Parcelable
import androidx.annotation.StringRes
import com.example.habits.R
import kotlinx.parcelize.Parcelize

@Parcelize
enum class HabitType(@StringRes val typeName: Int) : Parcelable {
    GOOD_HABIT(R.string.good_habit),
    BAD_HABIT(R.string.bad_habit)
}