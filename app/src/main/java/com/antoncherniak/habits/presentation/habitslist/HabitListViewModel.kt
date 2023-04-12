package com.antoncherniak.habits.presentation.habitslist

import android.util.Log
import androidx.lifecycle.*
import com.antoncherniak.habits.domain.interactor.HabitListInteractor
import com.antoncherniak.habits.domain.model.HabitType
import kotlinx.coroutines.launch

class HabitListViewModel(
    private val habitListInteractor: HabitListInteractor
) : ViewModel() {

    private val _screenState: MutableLiveData<ListScreenState> =
        MutableLiveData(ListScreenState.Init)
    val screenState: LiveData<ListScreenState> = _screenState
    var searchQuery: String = ""
    var reversed: Boolean = false
    var sortType: Int = SortType.SORT_BY_PRIORITY.spinnerPosition
    var habitType: String = HabitType.GOOD_HABIT.name

init {
    Log.e("RAd", "INT")
}

    private fun setSearchQueryAndSort(

    ): ListScreenState {
        _screenState.value = ListScreenState.Loading
        Log.e("TAGGG", "type = ${habitType} q = ${searchQuery}, rev =${reversed}, st = ${sortType}")
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
                }.onEach {
                    Log.e("TAGGG", "1 = ${it.title}")

                }
            _screenState.value = ListScreenState.Data(if (reversed) searchHabits else searchHabits.reversed())
            ListScreenState.Data(if (reversed) searchHabits else searchHabits.reversed())
        } catch (e: Exception) {
            Log.e("EXCEPTION", "E =  ${e.message}")
            ListScreenState.Error(e.message ?: "unknown error")
        }
        return result
    }

    private fun removeHabitById(
        habitId: Int,
        query: String = ""
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

    fun getHabits(    ) {
        Log.e("TAGGg", "GET")
        viewModelScope.launch {
            _screenState.value = setSearchQueryAndSort()
        }
    }

    fun removeHabit(
        habitId: Int,
        query: String = ""
    ) {
        viewModelScope.launch {
            _screenState.value = removeHabitById(habitId, query)
        }
    }
}