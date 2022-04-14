package com.example.habits.domain.repository

import com.example.habits.data.model.HabitItem

interface HabitsListRepository {

    fun getHabits(): List<HabitItem>

    fun removeHabit(habit: HabitItem)

    fun setCheckForHabit(isChecked: Boolean, id: Int)

    fun saveAllHabits(habitsList: List<HabitItem>)
}