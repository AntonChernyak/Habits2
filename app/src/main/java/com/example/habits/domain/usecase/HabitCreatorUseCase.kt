package com.example.habits.domain.usecase

import com.example.habits.data.model_dto.HabitUidDto
import com.example.habits.data.model_vo.HabitItem
import com.example.habits.domain.repository.HabitCreatorRemoteRepository
import com.example.habits.domain.repository.HabitCreatorLocalRepository

class HabitCreatorUseCase(
    private val localRepository: HabitCreatorLocalRepository,
    private val remoteRepository: HabitCreatorRemoteRepository
) {

    suspend fun addHabit(habit: HabitItem): HabitUidDto{
       // localRepository.addHabit(habit)
        return remoteRepository.addHabit(habit)
    }

    suspend fun replaceHabit(newHabit: HabitItem): HabitUidDto{
        //localRepository.replaceHabit(newHabit)
        return remoteRepository.replaceHabit(newHabit)
    }

    suspend fun removeHabit(habit: HabitItem, habitUidDto: HabitUidDto){
        //localRepository.removeHabit(habit)
        remoteRepository.removeHabit(habitUidDto)
    }
}