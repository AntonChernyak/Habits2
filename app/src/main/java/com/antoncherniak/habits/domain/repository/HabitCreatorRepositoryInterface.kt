package com.antoncherniak.habits.domain.repository

import com.antoncherniak.habits.domain.model.HabitModel

interface HabitCreatorRepositoryInterface : BaseRepository{
    fun addHabit(habit: HabitModel)

    fun updateHabit(habit: HabitModel)

    fun getHabitById(habitId: Int): HabitModel
}