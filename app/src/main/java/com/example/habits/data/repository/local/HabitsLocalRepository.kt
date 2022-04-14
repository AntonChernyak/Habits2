package com.example.habits.data.repository.local

import com.example.habits.data.database.HabitDao
import com.example.habits.data.model.HabitItem
import com.example.habits.domain.repository.HabitCreatorRepository
import com.example.habits.domain.repository.HabitsListRepository

class HabitsLocalRepository(private val habitDao: HabitDao): HabitsListRepository, HabitCreatorRepository  {

    override fun addHabit(habit: HabitItem) {
        habitDao.addHabit(habit)
    }

    override fun replaceHabit(newHabit: HabitItem) {
        habitDao.updateHabit(newHabit)
    }

    override fun getHabits(): List<HabitItem> {
        return habitDao.getAllHabits()
    }

    override fun removeHabit(habit: HabitItem) {
        habitDao.deleteHabit(habit)
    }

    override fun setCheckForHabit(isChecked: Boolean, id: Int) {
        habitDao.updateCheck(isChecked, id)
    }

    override fun saveAllHabits(habitsList: List<HabitItem>) {
        habitDao.saveAllHabits(habitsList)
    }

}