package com.example.habits.domain.usecase

import com.example.habits.data.model_vo.HabitItem
import com.example.habits.domain.repository.HabitCreatorRepository

class HabitCreatorUseCase(
    private val localRepository: HabitCreatorRepository,
    private val remoteRepository: HabitCreatorRepository
) {

    suspend fun addHabit(habit: HabitItem){
       // localRepository.addHabit(habit)
        remoteRepository.addHabit(habit)
    }

    suspend fun replaceHabit(newHabit: HabitItem){
        //localRepository.replaceHabit(newHabit)
        remoteRepository.replaceHabit(newHabit)
    }

    suspend fun removeHabit(habit: HabitItem){
        //localRepository.removeHabit(habit)
        remoteRepository.removeHabit(habit)
    }
}