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

    private fun getInitHabits(
        habitType: String,
        query: String,
        sortType: Int,
        reversed: Boolean
    ): ListScreenState {
        _screenState.value = ListScreenState.Loading
        val result = try {
            habitsList.clear()
            habitsList.addAll(habitListInteractor.getHabits().filter { it.type.name == habitType })
            setSearchQueryAndSort(query, sortType, reversed)
        } catch (e: Exception) {
            ListScreenState.Error(e.message ?: "unknown error")
        }
        return result
    }

    private fun setSearchQueryAndSort(
        searchQuery: String,
        sortType: Int,
        reversed: Boolean
    ): ListScreenState {
        _screenState.value = ListScreenState.Loading
        val result = try {
            val searchHabits =
                habitsList
                    .filter {
                        it.title.contains(searchQuery.trim())
                    }
                    .sortedBy {
                        if (sortType == SortType.SORT_BY_NAME.spinnerPosition) {
                            it.title
                        } else it.priority.spinnerPos.toString()
                    }
            ListScreenState.Data(if (reversed) searchHabits else searchHabits.reversed())
        } catch (e: Exception) {
            ListScreenState.Error(e.message ?: "unknown error")
        }
        return result
    }

    private fun removeHabitById(
        habit: HabitModel,
        query: String,
        sortType: Int,
        reversed: Boolean
    ): ListScreenState {
        _screenState.value = ListScreenState.Loading
        val result = try {
            habitListInteractor.removeHabit(habit.id)
            habitsList.remove(habit)
            setSearchQueryAndSort(query, sortType, reversed)
        } catch (e: Exception) {
            ListScreenState.Error(e.message ?: "unknown error")
        }
        return result
    }

    fun getHabits(
        type: String,
        query: String,
        sortType: Int,
        reversed: Boolean
    ) {
        viewModelScope.launch {
            _screenState.value = getInitHabits(type, query, sortType, reversed)
        }
    }

    fun searchAndSortHabits(
        query: String,
        sortType: Int = SortType.SORT_BY_PRIORITY.spinnerPosition,
        reversed: Boolean = false
    ) {
        viewModelScope.launch {
            _screenState.value = setSearchQueryAndSort(query, sortType, reversed)
        }
    }

    fun removeHabit(
        habit: HabitModel,
        query: String,
        sortType: Int,
        reversed: Boolean
    ) {
        viewModelScope.launch {
            _screenState.value = removeHabitById(habit, query, sortType, reversed)
        }
    }
}