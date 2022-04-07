package com.example.habits.domain.usecase

import com.example.habits.data.model.HabitItem
import com.example.habits.domain.repository.HabitsListRepository

class HabitsListUseCase(private val repository: HabitsListRepository) {

    fun getHabits() : List<HabitItem>{
        return repository.getHabits().sortedBy { it.priority }.reversed().toMutableList()
    }

    fun removeHabit(habit: HabitItem) {
        repository.removeHabit(habit)
    }

    fun setCheckForHabit(habit: HabitItem) {
        repository.setCheckForHabit(habit)
    }

    fun getSearchHabits(query: String) : List<HabitItem> {
        return getHabits().filter { it.title.uppercase().startsWith(query.uppercase()) }
    }
}