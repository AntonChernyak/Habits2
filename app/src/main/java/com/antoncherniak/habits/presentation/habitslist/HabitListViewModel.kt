package com.antoncherniak.habits.presentation.habitslist

import androidx.lifecycle.*
import com.antoncherniak.habits.domain.interactor.HabitListInteractor
import com.antoncherniak.habits.domain.model.HabitType
import kotlinx.coroutines.launch

class HabitListViewModel(
    private val habitListInteractor: HabitListInteractor
) : ViewModel() {

    private val _screenState: MutableLiveData<ListScreenState> =
        MutableLiveData(ListScreenState.Init)
    private val _searchSettings = MutableLiveData(SearchSettings())
    val screenState: LiveData<ListScreenState> = _screenState
    val searchSettings: LiveData<SearchSettings> = _searchSettings

    private fun setSearchQueryAndSort(
    ): ListScreenState {
        _screenState.value = ListScreenState.Loading
        val result = try {
            val searchHabits = habitListInteractor.getHabits()
                .filter {
                    it.type == _searchSettings.value?.habitType &&
                            it.title.contains(_searchSettings.value?.searchQuery?.trim() ?: "")
                }
                .sortedBy {
                    when (_searchSettings.value?.sortType) {
                        SortType.SORT_BY_NAME -> it.title
                        SortType.SORT_BY_PRIORITY -> it.priority.spinnerPos.toString()
                        else -> it.priority.spinnerPos.toString()
                    }
                }
            ListScreenState.Data(if (_searchSettings.value?.reversed == true) searchHabits else searchHabits.reversed())
        } catch (e: Exception) {
            ListScreenState.Error(e.message ?: "unknown error")
        }
        return result
    }

    private fun removeHabitById(
        habitId: Int,
    ): ListScreenState {
        _screenState.value = ListScreenState.Loading
        val result = try {
            habitListInteractor.removeHabit(habitId)
            setSearchQueryAndSort()
        } catch (e: Exception) {
            ListScreenState.Error(e.message ?: "unknown error")
        }
        return result
    }

    fun getHabits() {
        viewModelScope.launch {
            _screenState.value = setSearchQueryAndSort()
        }
    }

    fun removeHabit(habitId: Int) {
        viewModelScope.launch {
            _screenState.value = removeHabitById(habitId)
        }
    }

    fun setSearchString(query: String) {
        _searchSettings.value = _searchSettings.value?.copy(
            searchQuery = query
        )
    }

    fun getSearchString(): String = _searchSettings.value?.searchQuery ?: ""


    fun setReversed(reversed: Boolean) {
        _searchSettings.value = _searchSettings.value?.copy(
            reversed = reversed
        )
    }

    fun getReversed(): Boolean = _searchSettings.value?.reversed ?: false

    fun setSortedType(sortType: SortType) {
        _searchSettings.value = _searchSettings.value?.copy(
            sortType = sortType
        )
    }

    fun getSortedSpinnerPosition(): Int = _searchSettings.value?.sortType?.spinnerPosition ?: 0

    fun setHabitType(habitTypeName: String) {
        _searchSettings.value = _searchSettings.value?.copy(
            habitType = HabitType.valueOf(habitTypeName)
        )
    }
}