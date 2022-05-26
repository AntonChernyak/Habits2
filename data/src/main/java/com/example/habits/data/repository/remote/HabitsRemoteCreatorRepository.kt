package com.example.habits.data.repository.remote

import com.example.habits.domain.models.mapper.HabitMapper
import com.example.habits.domain.models.model_dto.HabitUidDto
import com.example.habits.data.database.model_vo.HabitItem
import com.example.habits.data.network.HabitApiInterface
import com.example.habits.domain.repository.HabitCreatorRemoteRepository

class HabitsRemoteCreatorRepository(private val habitApiInterface: HabitApiInterface) :
    HabitCreatorRemoteRepository {

    override suspend fun addHabit(habit: HabitItem): HabitUidDto {
        return habitApiInterface.putHabit(
            HabitMapper().toDataTransferObject(habit)
        )
    }

    override suspend fun replaceHabit(newHabit: HabitItem): HabitUidDto {
       return habitApiInterface.putHabit(
            HabitMapper().toDataTransferObject(newHabit)
        )
    }

    override suspend fun removeHabit(habitUidDto: HabitUidDto) {
        habitApiInterface.deleteHabit(habitUidDto)
    }

}