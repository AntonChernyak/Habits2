package com.example.habits.domain.usecase

import com.example.habits.domain.model_dto.HabitDoneDto
import com.example.habits.domain.model_dto.HabitDto
import com.example.habits.domain.model_dto.HabitUidDto
import com.example.habits.domain.repository.HabitsListLocalRepository
import com.example.habits.domain.repository.HabitsListRemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HabitsListInteractor @Inject constructor(
    private val localRepository: HabitsListLocalRepository,
    private val remoteRepository: HabitsListRemoteRepository
) {

    suspend fun removeHabit(habit: HabitDto, habitUidDto: HabitUidDto) {
        localRepository.removeHabit(habit)
        remoteRepository.removeHabit(habitUidDto)
    }

    suspend fun setCheckForHabit(habitDone: HabitDoneDto, doneDates: List<Int>) {
        localRepository.setCheckForHabit(doneDates, habitDone.habitUid)
        remoteRepository.setCheckForHabit(habitDone)
    }

    suspend fun getHabitsFromNetwork(): List<HabitDto> {
        return remoteRepository.getHabits()
    }

    suspend fun saveAllHabits(list: List<HabitDto>) {
        localRepository.saveAllHabits(list)
    }

    fun getHabits(): Flow<List<HabitDto>> {
        return localRepository.getHabits()
    }

    fun getSearchHabits(query: String): Flow<List<HabitDto>> {
        return localRepository.getSearchHabits(query)
    }

    fun getSortedHabits(position: Int, reversed: Boolean): Flow<List<HabitDto>> {

        return when (position) {
            0 -> {
                if (reversed) localRepository.getSortedHabitsByPriorityASC()
                else localRepository.getSortedHabitsByPriorityDESC()
            }
            else -> {
                if (reversed) localRepository.getSortedHabitsByTitleASC()
                else localRepository.getSortedHabitsByTitleDESC()
            }
        }
    }
}