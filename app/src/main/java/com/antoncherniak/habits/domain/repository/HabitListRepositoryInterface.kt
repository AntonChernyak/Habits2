package com.antoncherniak.habits.domain.repository

import com.antoncherniak.habits.domain.model.HabitModel

interface HabitListRepositoryInterface {
    fun getHabits(): List<HabitModel>

    fun removeHabit(habitId: Int)
}