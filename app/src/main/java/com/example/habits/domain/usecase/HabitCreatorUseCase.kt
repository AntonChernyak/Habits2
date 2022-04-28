package com.example.habits.domain.usecase

import com.example.habits.data.model.HabitItem
import com.example.habits.domain.repository.HabitCreatorRepository

class HabitCreatorUseCase(
    private val localRepository: HabitCreatorRepository,
    private val remoteRepository: HabitCreatorRepository
) {

    fun addHabit(habit: HabitItem){
        localRepository.addHabit(habit)
        remoteRepository.addHabit(habit)
    }

    fun replaceHabit(newHabit: HabitItem){
        localRepository.replaceHabit(newHabit)
        remoteRepository.replaceHabit(newHabit)
    }

    fun removeHabit(habit: HabitItem){
        localRepository.removeHabit(habit)
        remoteRepository.removeHabit(habit)
    }
}