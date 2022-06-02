package com.example.habits.data.repository.local

import com.example.habits.data.database.HabitDao
import com.example.habits.data.mapper.HabitMapper
import com.example.habits.domain.model_dto.HabitDto
import com.example.habits.domain.repository.HabitCreatorLocalRepository

class HabitsLocalCreatorRepository(private val habitDao: HabitDao): HabitCreatorLocalRepository {

    private val mapper = HabitMapper()

    override suspend fun addHabit(habit: HabitDto) {
        habitDao.addHabit(mapper.toViewObject(habit))
    }

    override suspend fun replaceHabit(newHabit: HabitDto) {
        habitDao.updateHabit(mapper.toViewObject(newHabit))
    }

    override suspend fun removeHabit(habit: HabitDto) {
        habitDao.deleteHabit(mapper.toViewObject(habit))
    }
}