package com.example.habits.domain.repository

import com.example.data.model_dto.HabitUidDto
import com.example.data.model_vo.HabitItem

interface HabitCreatorRemoteRepository {

    suspend fun addHabit(habit: HabitItem): HabitUidDto

    suspend fun replaceHabit(newHabit: HabitItem): HabitUidDto

    suspend fun removeHabit(habitUidDto: HabitUidDto)
}