package com.example.data.repository.remote

import com.example.data.mapper.HabitMapper
import com.example.data.model_dto.HabitUidDto
import com.example.data.model_vo.HabitItem
import com.example.data.network.HabitApiInterface
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