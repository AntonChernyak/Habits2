package com.example.habits.data.repository.remote

import com.example.habits.domain.model_dto.HabitDoneDto
import com.example.habits.domain.model_dto.HabitDto
import com.example.habits.domain.model_dto.HabitUidDto
import com.example.habits.data.network.HabitApiInterface
import com.example.habits.domain.repository.HabitsListRemoteRepository

class HabitsRemoteListRepository(private val apiInterface: HabitApiInterface) : HabitsListRemoteRepository {

    override suspend fun getHabits(): List<HabitDto> {
        return apiInterface.getHabits()
    }

    override suspend fun removeHabit(habitUidDto: HabitUidDto) {
        apiInterface.deleteHabit(habitUidDto)
    }

    override suspend fun setCheckForHabit(habitDone: HabitDoneDto) {
        apiInterface.checkHabit(habitDone)
    }

}