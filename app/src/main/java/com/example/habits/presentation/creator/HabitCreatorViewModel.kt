package com.example.habits.presentation.creator

import androidx.lifecycle.ViewModel
import com.example.habits.data.model.HabitItem
import com.example.habits.domain.usecase.HabitCreatorUseCase

class HabitCreatorViewModel(private val habitCreatorUseCase: HabitCreatorUseCase): ViewModel() {

    fun addHabit(habitItem: HabitItem){
        habitCreatorUseCase.addHabit(habitItem)
    }

    fun replaceHabit(habitItem: HabitItem){
        habitCreatorUseCase.replaceHabit(habitItem)
    }

    fun removeLastHabit(){
        habitCreatorUseCase.removeLastHabit()
    }

}