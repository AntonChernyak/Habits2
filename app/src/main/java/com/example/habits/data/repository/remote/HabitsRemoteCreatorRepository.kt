package com.example.habits.data.repository.remote

import com.example.habits.data.mapper.HabitMapper
import com.example.habits.data.model_vo.HabitItem
import com.example.habits.data.network.HabitApiInterface
import com.example.habits.domain.repository.HabitCreatorRepository

class HabitsRemoteCreatorRepository(private val habitApiInterface: HabitApiInterface) :
    HabitCreatorRepository {

    override suspend fun addHabit(habit: HabitItem) {
        habitApiInterface.putHabit(
            HabitMapper().toDataTransferObject(habit)
        )
    }

    override suspend fun replaceHabit(newHabit: HabitItem) {
        habitApiInterface.putHabit(
            HabitMapper().toDataTransferObject(newHabit)
        )
    }

    override suspend fun removeHabit(habit: HabitItem) {
        habitApiInterface.deleteHabit(habit.id)
    }

}