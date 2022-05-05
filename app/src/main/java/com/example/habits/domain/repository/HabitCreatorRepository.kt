package com.example.habits.domain.repository

import com.example.habits.data.model_vo.HabitItem

interface HabitCreatorRepository {

    suspend fun addHabit(habit: HabitItem)

    suspend fun replaceHabit(newHabit: HabitItem)

    suspend fun removeHabit(habit: HabitItem)
}