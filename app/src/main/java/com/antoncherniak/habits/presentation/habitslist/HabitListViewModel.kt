package com.antoncherniak.habits.presentation.habitslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.antoncherniak.habits.domain.interactor.HabitListInteractor
import com.antoncherniak.habits.domain.model.HabitModel
import com.antoncherniak.habits.presentation.ScreenState

class HabitListViewModel(
    private val habitListInteractor: HabitListInteractor
) : ViewModel() {

    private val _screenState: MutableLiveData<ScreenState> = MutableLiveData()
    private val habitsList: MutableList<HabitModel> = mutableListOf()
    val screenState: LiveData<ScreenState> = _screenState

    init {
        getInitHabits()
    }

    private fun getInitHabits() {
        _screenState.value = ScreenState.Loading
        try {
            habitsList.addAll(habitListInteractor.getHabits())
            _screenState.value = ScreenState.Data(habitsList)
        } catch (e: Exception) {
            _screenState.value  = ScreenState.Error(e.message ?: "unknown error")
        }
    }

    fun searchHabit(searchQuery: String) {
        _screenState.value = ScreenState.Loading
        try {
            val searchHabits = habitsList.filter { it.title.contains(searchQuery.trim()) }
            _screenState.value = ScreenState.Data(searchHabits)
        } catch (e : Exception) {
            _screenState.value  = ScreenState.Error(e.message ?: "unknown error")
        }
    }


}