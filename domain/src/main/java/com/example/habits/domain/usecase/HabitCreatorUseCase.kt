package com.example.habits.domain.usecase

import com.example.habits.domain.model_dto.HabitDto
import com.example.habits.domain.model_dto.HabitUidDto
import com.example.habits.domain.repository.HabitCreatorRemoteRepository
import com.example.habits.domain.repository.HabitCreatorLocalRepository
import javax.inject.Inject

class HabitCreatorUseCase @Inject constructor(
    val localRepository: HabitCreatorLocalRepository,
    private val remoteRepository: HabitCreatorRemoteRepository
) {

    suspend fun addHabit(habit: HabitDto): HabitUidDto {
        // localRepository.addHabit(habit)
        return remoteRepository.addHabit(habit)
    }

    suspend fun replaceHabit(newHabit: HabitDto): HabitUidDto {
        //localRepository.replaceHabit(newHabit)
        return remoteRepository.replaceHabit(newHabit)
    }

    suspend fun removeHabit(habit: HabitDto, habitUidDto: HabitUidDto) {
        //localRepository.removeHabit(habit)
        remoteRepository.removeHabit(habitUidDto)
    }
}