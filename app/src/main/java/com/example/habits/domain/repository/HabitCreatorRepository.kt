package com.example.habits.domain.repository

import com.example.habits.data.model.HabitItem

interface HabitCreatorRepository {

    fun addHabit(habit: HabitItem)

    fun replaceHabit(newHabit: HabitItem)
}