package com.example.habits.domain.repository

import com.example.habits.domain.model_dto.HabitDoneDto
import com.example.habits.domain.model_dto.HabitDto
import com.example.habits.domain.model_dto.HabitUidDto

interface HabitsListRemoteRepository {

    suspend fun getHabits(): List<HabitDto>

    suspend fun removeHabit(habitUidDto: HabitUidDto)

    suspend fun setCheckForHabit(habitDone: HabitDoneDto)
}