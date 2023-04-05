package com.antoncherniak.habits.presentation

import com.antoncherniak.habits.domain.model.HabitModel

sealed interface ScreenState {
    object Loading : ScreenState

    object Init : ScreenState

    class Data(val habits: List<HabitModel>) : ScreenState

    class Error(val errorMessage: String) : ScreenState
}