package com.example.habits.domain.usecase

import com.example.habits.data.model.HabitItem
import com.example.habits.domain.repository.HabitsListRepository

class HabitsListUseCase(private val repository: HabitsListRepository) {

    fun getMovies() : List<HabitItem>{
        return repository.getHabits()
    }
}