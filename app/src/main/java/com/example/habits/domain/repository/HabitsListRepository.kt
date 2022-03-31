package com.example.habits.domain.repository

import com.example.habits.data.model.HabitItem

interface HabitsListRepository {

    fun getHabits(): List<HabitItem>

}