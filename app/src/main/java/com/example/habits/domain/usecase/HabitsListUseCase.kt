package com.example.habits.domain.usecase

import androidx.lifecycle.LiveData
import com.example.habits.data.model_dto.HabitDoneDto
import com.example.habits.data.model_vo.HabitItem
import com.example.habits.domain.repository.HabitsListLocalRepository
import com.example.habits.domain.repository.HabitsListRemoteRepository

class HabitsListUseCase(
    private val localRepository: HabitsListLocalRepository,
    private val remoteRepository: HabitsListRemoteRepository
) {

    suspend fun removeHabit(habit: HabitItem) {
        localRepository.removeHabit(habit)
        remoteRepository.removeHabit(habit)
    }

    suspend fun setCheckForHabit(isChecked: Boolean, id: Int, habitDone: HabitDoneDto) {
        localRepository.setCheckForHabit(isChecked, id)
        remoteRepository.setCheckForHabit(habitDone)
    }

    suspend fun getHabitsFromNetwork(): List<HabitItem> {
        return remoteRepository.getHabits()
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