package com.example.habits.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habits.domain.usecase.HabitCreatorUseCase
import com.example.habits.domain.usecase.HabitsListUseCase
import com.example.habits.presentation.creator.HabitCreatorViewModel
import com.example.habits.presentation.list.HabitsListViewModel
import javax.inject.Inject

class HabitViewModelFactory @Inject constructor(
    val habitsListUseCase: HabitsListUseCase,
    val habitCreatorUseCase: HabitCreatorUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            HabitsListViewModel::class.java -> {
                HabitsListViewModel(habitsListUseCase)
            }
            HabitCreatorViewModel::class.java -> {
                HabitCreatorViewModel(habitCreatorUseCase)
            }
            else -> {
                throw IllegalStateException("Unknown ViewModel class")
            }
        }
        return viewModel as T

    }
}