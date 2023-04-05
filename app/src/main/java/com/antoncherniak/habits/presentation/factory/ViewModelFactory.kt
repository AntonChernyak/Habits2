package com.antoncherniak.habits.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.antoncherniak.habits.domain.interactor.HabitCreatorInteractor
import com.antoncherniak.habits.domain.interactor.HabitListInteractor
import com.antoncherniak.habits.presentation.habitcreator.HabitCreatorViewModel
import com.antoncherniak.habits.presentation.habitslist.HabitListViewModel

class ViewModelFactory(
    private val habitListInteractor: HabitListInteractor,
    private val habitCreatorInteractor: HabitCreatorInteractor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            HabitListViewModel::class.java -> HabitListViewModel(habitListInteractor)
            HabitCreatorViewModel::class.java -> HabitCreatorViewModel(habitCreatorInteractor)
            else -> error("Unknown ViewModel class")
        }
        return viewModel as T
    }
}