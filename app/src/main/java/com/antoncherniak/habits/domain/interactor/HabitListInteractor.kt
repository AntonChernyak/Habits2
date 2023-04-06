package com.antoncherniak.habits.domain.interactor

import com.antoncherniak.habits.domain.model.HabitModel
import com.antoncherniak.habits.domain.repository.HabitListRepositoryInterface

class HabitListInteractor(
    private val habitListRepository: HabitListRepositoryInterface
): HabitListRepositoryInterface {

    override fun getHabits(): List<HabitModel>  {
        return habitListRepository.getHabits()
    }

    override fun removeHabit(habitId: Int) = habitListRepository.removeHabit(habitId)

}