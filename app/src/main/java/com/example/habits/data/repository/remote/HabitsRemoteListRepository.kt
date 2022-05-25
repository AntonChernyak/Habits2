package com.example.habits.data.repository.remote

import com.example.habits.data.mapper.HabitMapper
import com.example.habits.data.model_dto.HabitDoneDto
import com.example.habits.data.model_dto.HabitDto
import com.example.habits.data.model_vo.HabitItem
import com.example.habits.data.network.HabitApiInterface
import com.example.habits.domain.repository.HabitsListRemoteRepository

class HabitsRemoteListRepository(private val apiInterface: HabitApiInterface) : HabitsListRemoteRepository {

    override suspend fun getHabits(): List<HabitDto> {
        //return HabitMapper().toViewObject(apiInterface.getHabits())
        return apiInterface.getHabits()
    }

    override suspend fun removeHabit(habit: HabitItem) {
        apiInterface.deleteHabit(habit.id)
    }

    override suspend fun setCheckForHabit(habitDone: HabitDoneDto) {
        apiInterface.checkHabit(habitDone)
    }

}