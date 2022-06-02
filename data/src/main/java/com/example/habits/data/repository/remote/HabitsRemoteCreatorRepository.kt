package com.example.habits.data.repository.remote

import com.example.habits.domain.model_dto.HabitUidDto
import com.example.habits.data.network.HabitApiInterface
import com.example.habits.domain.model_dto.HabitDto
import com.example.habits.domain.repository.HabitCreatorRemoteRepository

class HabitsRemoteCreatorRepository(private val habitApiInterface: HabitApiInterface) :
    HabitCreatorRemoteRepository {

    override suspend fun addHabit(habit: HabitDto): HabitUidDto {
        return habitApiInterface.putHabit(habit)
    }

    override suspend fun replaceHabit(newHabit: HabitDto): HabitUidDto {
       return habitApiInterface.putHabit(newHabit)
    }

    override suspend fun removeHabit(habitUidDto: HabitUidDto) {
        habitApiInterface.deleteHabit(habitUidDto)
    }

}