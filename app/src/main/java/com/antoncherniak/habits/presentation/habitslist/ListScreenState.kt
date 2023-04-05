package com.antoncherniak.habits.presentation.habitslist

import com.antoncherniak.habits.domain.model.HabitModel

sealed interface ListScreenState {
    object Loading : ListScreenState

    object Init : ListScreenState

    class Data(val habits: List<HabitModel>) : ListScreenState

    class Error(val errorMessage: String) : ListScreenState
}