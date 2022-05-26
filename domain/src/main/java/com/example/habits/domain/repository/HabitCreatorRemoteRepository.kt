package com.example.habits.domain.repository

import com.example.habits.domain.model_dto.HabitDto
import com.example.habits.domain.model_dto.HabitUidDto

interface HabitCreatorRemoteRepository {

    suspend fun addHabit(habit: HabitDto): HabitUidDto

    suspend fun replaceHabit(newHabit: HabitDto): HabitUidDto

    suspend fun removeHabit(habitUidDto: HabitUidDto)
}