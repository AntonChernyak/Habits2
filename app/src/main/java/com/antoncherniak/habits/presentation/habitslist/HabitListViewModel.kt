package com.antoncherniak.habits.presentation.habitslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoncherniak.habits.domain.interactor.HabitListInteractor
import kotlinx.coroutines.launch

class HabitListViewModel(
    private val habitListInteractor: HabitListInteractor
) : ViewModel() {

    private val _screenState: MutableLiveData<ListScreenState> =
        MutableLiveData(ListScreenState.Init)
    val screenState: LiveData<ListScreenState> = _screenState

    private fun setSearchQueryAndSort(
        habitType: String,
        searchQuery: String,
        sortType: Int,
        reversed: Boolean
    ): ListScreenState {
        _screenState.value = ListScreenState.Loading
        val result = try {
            val searchHabits = habitListInteractor.getHabits().filter { it.type.name == habitType }
                .filter {
                    it.title.contains(searchQuery.trim())
                }
                .sortedBy {
                    when (sortType) {
                        SortType.SORT_BY_NAME.spinnerPosition -> it.title
                        SortType.SORT_BY_PRIORITY.spinnerPosition -> it.priority.spinnerPos.toString()
                        else -> it.priority.spinnerPos.toString()
                    }
                }
            ListScreenState.Data(if (reversed) searchHabits else searchHabits.reversed())
        } catch (e: Exception) {
            ListScreenState.Error(e.message ?: "unknown error")
        }
        return result
    }

    private fun removeHabitById(
        habitId: Int,
        habitType: String,
        query: String,
        sortType: Int,
        reversed: Boolean
    ): ListScreenState {
        _screenState.value = ListScreenState.Loading
        val result = try {
            habitListInteractor.removeHabit(habitId)
            setSearchQueryAndSort(habitType, query, sortType, reversed)
        } catch (e: Exception) {
            ListScreenState.Error(e.message ?: "unknown error")
        }
        return result
    }

    fun getHabits(
        habitType: String,
        query: String,
        sortType: Int,
        reversed: Boolean
    ) {
        viewModelScope.launch {
            _screenState.value = setSearchQueryAndSort(habitType, query, sortType, reversed)
        }
    }

    fun removeHabit(
        habitId: Int,
        habitType: String,
        query: String,
        sortType: Int,
        reversed: Boolean
    ) {
        viewModelScope.launch {
            _screenState.value = removeHabitById(habitId, habitType, query, sortType, reversed)
        }
    }
}