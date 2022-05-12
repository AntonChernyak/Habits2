package com.example.habits.domain.repository

import com.example.habits.data.model_dto.HabitDoneDto
import com.example.habits.data.model_vo.HabitItem

interface HabitsListRemoteRepository {

    suspend fun getHabits(): List<HabitItem>

    suspend fun removeHabit(habit: HabitItem)

    suspend fun setCheckForHabit(habitDone: HabitDoneDto)
}