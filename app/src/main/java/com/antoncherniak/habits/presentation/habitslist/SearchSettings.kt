package com.antoncherniak.habits.presentation.habitslist

import com.antoncherniak.habits.domain.model.HabitType

data class SearchSettings(
    val habitType: HabitType = HabitType.GOOD_HABIT,
    val searchQuery: String = "",
    val reversed: Boolean = false,
    val sortType: SortType = SortType.SORT_BY_PRIORITY
)