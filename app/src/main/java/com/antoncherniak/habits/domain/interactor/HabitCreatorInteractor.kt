package com.antoncherniak.habits.domain.interactor

import com.antoncherniak.habits.domain.model.HabitModel
import com.antoncherniak.habits.domain.repository.HabitCreatorRepositoryInterface

class HabitCreatorInteractor(private val habitCreatorRepository: HabitCreatorRepositoryInterface) :
    HabitCreatorRepositoryInterface {

    override fun addHabit(habit: HabitModel) = habitCreatorRepository.addHabit(habit)

    override fun updateHabit(habit: HabitModel) = habitCreatorRepository.updateHabit(habit)
    override fun getHabitById(habitId: Int): HabitModel = habitCreatorRepository.getHabitById(habitId)

    override fun removeHabit(habitId: Int) = habitCreatorRepository.removeHabit(habitId)
}