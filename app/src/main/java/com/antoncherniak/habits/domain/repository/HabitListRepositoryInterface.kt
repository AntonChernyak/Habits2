package com.antoncherniak.habits.domain.repository

import com.antoncherniak.habits.domain.model.HabitModel

interface HabitListRepositoryInterface : BaseRepository {
    fun getHabits(): List<HabitModel>
}