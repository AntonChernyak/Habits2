package com.example.habits.domain.usecase

import com.example.habits.data.model.HabitItem
import com.example.habits.domain.repository.HabitsListRepository

class HabitsListUseCase(private val repository: HabitsListRepository) {

    fun getHabits(): List<HabitItem> {
        return repository.getHabits().sortedBy { it.priority }.reversed().toMutableList()
    }

    fun removeHabit(habit: HabitItem) {
        repository.removeHabit(habit)
    }

    fun setCheckForHabit(habit: HabitItem) {
        repository.setCheckForHabit(habit)
    }

    fun getSearchHabits(query: String): List<HabitItem> {
        return getHabits().filter { it.title.uppercase().startsWith(query.uppercase()) }
    }

    fun getSortedHabits(position: Int, reversed: Boolean): List<HabitItem> {
        return when (position) {
            0 -> {
                if (reversed) getHabits()
                else repository.getHabits().sortedBy { it.priority }.toMutableList()
            }
            1 -> {
                if (reversed) repository.getHabits().sortedBy { it.title }.reversed().toMutableList()
                else repository.getHabits().sortedBy { it.title }.toMutableList()
            }
            else -> emptyList()
        }
    }
}