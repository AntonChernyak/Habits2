package com.antoncherniak.habits.presentation

import com.antoncherniak.habits.domain.model.HabitModel

sealed interface ScreenState {
    object Loading : ScreenState

    object Init : ScreenState

    class Data(habits: List<HabitModel>) : ScreenState

    class Error(errorMessage: String) : ScreenState
}