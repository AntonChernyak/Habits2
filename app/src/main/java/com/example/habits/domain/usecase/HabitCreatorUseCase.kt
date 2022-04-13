package com.example.habits.domain.usecase

import com.example.habits.data.model.HabitItem
import com.example.habits.domain.repository.HabitCreatorRepository

class HabitCreatorUseCase(private val repository: HabitCreatorRepository) {

    fun addHabit(habit: HabitItem){
        repository.addHabit(habit)
    }

    fun replaceHabit(newHabit: HabitItem){
        repository.replaceHabit(newHabit)
    }

    fun removeLastHabit(){
        repository.removeLastHabit()
    }
}