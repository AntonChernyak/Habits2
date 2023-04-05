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

    private val _screenState: MutableLiveData<ScreenState> = MutableLiveData(ScreenState.Init)
    private val habitsList: MutableList<HabitModel> = mutableListOf()
    val screenState: LiveData<ScreenState> = _screenState

    private fun getInitHabits(habitType: String): ScreenState {
        _screenState.value = ScreenState.Loading
        val result = try {
            habitsList.addAll(habitListInteractor.getHabits())
            ScreenState.Data(habitsList.filter { it.type.name == habitType})
        } catch (e: Exception) {
            ScreenState.Error(e.message ?: "unknown error")
        }
        return result
    }

    private fun setSearchQuery(searchQuery: String): ScreenState {
        _screenState.value = ScreenState.Loading
        val result = try {
            val searchHabits = habitsList.filter { it.title.contains(searchQuery.trim()) }
            ScreenState.Data(searchHabits)
        } catch (e: Exception) {
            ScreenState.Error(e.message ?: "unknown error")
        }
        return result
    }

    private fun removeHabitById(habitId: Int): ScreenState {
        _screenState.value = ScreenState.Loading
        val result = try {
            habitListInteractor.removeHabit(habitId)
            habitsList.indexOfFirst { it.id == habitId}.apply {
                habitsList.removeAt(this)
            }
            ScreenState.Data(habitsList)
        } catch (e: Exception) {
            ScreenState.Error(e.message ?: "unknown error")
        }
        return result
    }

    fun getHabits(type: String) {
        _screenState.value = getInitHabits(type)
    }

    fun searchHabit(query: String) {
        _screenState.value = setSearchQuery(query)
    }

    fun removeHabit(habitId: Int) {
        _screenState.value = removeHabitById(habitId)
    }
}