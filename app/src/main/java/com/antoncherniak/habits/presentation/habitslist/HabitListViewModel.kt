package com.antoncherniak.habits.presentation.habitslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoncherniak.habits.domain.interactor.HabitListInteractor
import com.antoncherniak.habits.domain.model.HabitModel
import kotlinx.coroutines.launch

class HabitListViewModel(
    private val habitListInteractor: HabitListInteractor
) : ViewModel() {

    private val _screenState: MutableLiveData<ListScreenState> =
        MutableLiveData(ListScreenState.Init)
    private val habitsList: MutableList<HabitModel> = mutableListOf()
    val screenState: LiveData<ListScreenState> = _screenState

    private fun getInitHabits(habitType: String): ListScreenState {
        _screenState.value = ListScreenState.Loading
        val result = try {
            habitsList.clear()
            habitsList.addAll(habitListInteractor.getHabits())
            ListScreenState.Data(habitsList.filter { it.type.name == habitType })
        } catch (e: Exception) {
            ListScreenState.Error(e.message ?: "unknown error")
        }
        return result
    }

    private fun setSearchQuery(searchQuery: String): ListScreenState {
        _screenState.value = ListScreenState.Loading
        val result = try {
            val searchHabits = habitsList.filter { it.title.contains(searchQuery.trim()) }
            ListScreenState.Data(searchHabits)
        } catch (e: Exception) {
            ListScreenState.Error(e.message ?: "unknown error")
        }
        return result
    }

    private fun removeHabitById(habit: HabitModel): ListScreenState {
        _screenState.value = ListScreenState.Loading
        val result = try {
            habitListInteractor.removeHabit(habit.id)
            habitsList.remove(habit)
            ListScreenState.Data(habitsList)
        } catch (e: Exception) {
            ListScreenState.Error(e.message ?: "unknown error")
        }
        return result
    }

    fun getHabits(type: String) {
        viewModelScope.launch {
            _screenState.value = getInitHabits(type)
        }
    }

    fun searchHabit(query: String) {
        viewModelScope.launch {
            _screenState.value = setSearchQuery(query)
        }
    }

    fun removeHabit(habit: HabitModel) {
        viewModelScope.launch {
            _screenState.value = removeHabitById(habit)
        }
    }
}