package com.example.habits.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habits.domain.usecase.HabitCreatorInteractor
import com.example.habits.domain.usecase.HabitsListInteractor
import com.example.habits.presentation.creator.HabitCreatorViewModel
import com.example.habits.presentation.list.HabitsListViewModel

class HabitViewModelFactory(
    private val habitsListInteractor: HabitsListInteractor,
    private val habitCreatorInteractor: HabitCreatorInteractor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            HabitsListViewModel::class.java -> {
                HabitsListViewModel(habitsListInteractor)
            }
            HabitCreatorViewModel::class.java -> {
                HabitCreatorViewModel(habitCreatorInteractor)
            }
            else -> {
                throw IllegalStateException("Unknown ViewModel class")
            }
        }
        return viewModel as T
    }
}