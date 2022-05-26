package com.example.habits.domain.repository

import com.example.data.model_dto.HabitDoneDto
import com.example.data.model_dto.HabitDto
import com.example.data.model_dto.HabitUidDto

interface HabitsListRemoteRepository {

    suspend fun getHabits(): List<HabitDto>

    suspend fun removeHabit(habitUidDto: HabitUidDto)

    suspend fun setCheckForHabit(habitDone: HabitDoneDto)
}