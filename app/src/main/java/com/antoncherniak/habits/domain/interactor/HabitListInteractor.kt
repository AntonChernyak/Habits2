package com.antoncherniak.habits.domain.interactor

import com.antoncherniak.habits.domain.model.HabitModel
import com.antoncherniak.habits.domain.repository.HabitListRepositoryInterface

class HabitListInteractor(
    private val habitListRepository: HabitListRepositoryInterface
) {

    fun getHabits(): List<HabitModel> = habitListRepository.getHabits()

}