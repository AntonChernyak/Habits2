package com.example.habits.domain.repository

import com.example.habits.domain.model_dto.HabitDto

interface HabitCreatorLocalRepository {

    suspend fun addHabit(habit: HabitDto)

    suspend fun replaceHabit(newHabit: HabitDto)

    suspend fun removeHabit(habit: HabitDto)
}