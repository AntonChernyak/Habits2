package com.example.data.repository.remote

import com.example.data.model_dto.HabitDoneDto
import com.example.data.model_dto.HabitDto
import com.example.data.model_dto.HabitUidDto
import com.example.data.network.HabitApiInterface
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