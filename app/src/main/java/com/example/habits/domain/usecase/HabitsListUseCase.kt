package com.example.habits.domain.usecase

import com.example.habits.data.model.HabitItem
import com.example.habits.domain.repository.HabitsListRepository

class HabitsListUseCase(
    private val localRepository: HabitsListRepository,
    private val remoteRepository: HabitsListRepository
) {

    fun getHabits(): List<HabitItem> {
        val databaseList = localRepository.getHabits()
        val remoteList = remoteRepository.getHabits()
        return if(databaseList.isNullOrEmpty()) {
            localRepository.saveAllHabits(remoteList)
            remoteList
        } else databaseList
    }

    fun removeHabit(habit: HabitItem) {
        localRepository.removeHabit(habit)
        remoteRepository.removeHabit(habit)
    }

    fun setCheckForHabit(isChecked: Boolean, id: Int) {
        localRepository.setCheckForHabit(isChecked, id)
        remoteRepository.setCheckForHabit(isChecked, id)
    }

    fun getSearchHabits(query: String): List<HabitItem> {
        return getHabits().filter { it.title.uppercase().startsWith(query.uppercase()) }
    }

    fun getSortedHabits(position: Int, reversed: Boolean): List<HabitItem> {
        return when (position) {
            0 -> {
                if (reversed) getHabits()
                else getHabits().sortedBy { it.priority }.toMutableList()
            }
            1 -> {
                if (reversed) getHabits().sortedBy { it.title }.reversed()
                    .toMutableList()
                else getHabits().sortedBy { it.title }.toMutableList()
            }
            else -> emptyList()
        }
    }

/*    fun getHabits(position: Int, reversed: Boolean, query: String): List<HabitItem> {
        val searchItems = if (query.isEmpty()) repository.getHabits()
        else {
            repository.getHabits().filter { it.title.uppercase().startsWith(query.uppercase()) }

        }
        return getSortedHabits(position, reversed, searchItems)

    }*/
}