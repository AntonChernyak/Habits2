package com.example.habits.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habits.domain.usecase.HabitsListUseCase
import com.example.habits.presentation.list.HabitsListViewModel

class HabitViewModelFactory(private val habitsListUseCase: HabitsListUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitsListViewModel::class.java)) {
            return HabitsListViewModel(habitsListUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}