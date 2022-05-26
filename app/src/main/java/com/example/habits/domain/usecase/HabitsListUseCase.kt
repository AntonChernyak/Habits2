package com.example.habits.domain.usecase

import androidx.lifecycle.LiveData
import com.example.data.model_dto.HabitDoneDto
import com.example.data.model_dto.HabitDto
import com.example.data.model_dto.HabitUidDto
import com.example.data.model_vo.HabitItem
import com.example.habits.domain.repository.HabitsListLocalRepository
import com.example.habits.domain.repository.HabitsListRemoteRepository

class HabitsListUseCase(
    private val localRepository: HabitsListLocalRepository,
    private val remoteRepository: HabitsListRemoteRepository
) {

    suspend fun removeHabit(habit: HabitItem, habitUidDto: HabitUidDto) {
        localRepository.removeHabit(habit)
        remoteRepository.removeHabit(habitUidDto)
    }

    suspend fun setCheckForHabit(habitDone: HabitDoneDto, doneDates: List<Int>) {
       // localRepository.setCheckForHabit(doneDates, habitDone.habitUid)
        remoteRepository.setCheckForHabit(habitDone)
    }

    suspend fun getHabitsFromNetwork(): List<HabitDto> {
        return remoteRepository.getHabits()
    }

    suspend fun saveAllHabits(list: List<HabitItem>) {
        localRepository.saveAllHabits(list)
    }

    fun getHabits(): LiveData<List<HabitItem>> {
        return localRepository.getHabits()
    }

    fun getSearchHabits(query: String): LiveData<List<HabitItem>> {
        return localRepository.getSearchHabits(query)
    }

    fun getSortedHabits(position: Int, reversed: Boolean): LiveData<List<HabitItem>> {

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