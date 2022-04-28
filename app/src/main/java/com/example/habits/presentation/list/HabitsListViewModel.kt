package com.example.habits.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habits.data.model.HabitItem
import com.example.habits.domain.usecase.HabitsListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HabitsListViewModel(
    private val habitsUseCase: HabitsListUseCase
) : ViewModel() {

    private var habitsMutableLiveData = MutableLiveData<List<HabitItem>>()

    val habitsLiveData: LiveData<List<HabitItem>> = habitsMutableLiveData

    fun getHabits(): LiveData<List<HabitItem>> {
        return habitsUseCase.getHabits()
    }

    fun removeHabit(habitItem: HabitItem) {
        viewModelScope.launch(Dispatchers.IO) {
            habitsUseCase.removeHabit(habitItem)
        }
        getHabits()
    }

    fun setCheckForHabit(isChecked: Boolean, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            habitsUseCase.setCheckForHabit(isChecked, id)
        }
    }

    fun getSearchList(query: String): LiveData<List<HabitItem>> {
        return habitsUseCase.getSearchHabits(query)
    }

    fun getSortedHabits(position: Int, reversed: Boolean) {
        //   val sortedList = habitsUseCase.getSortedHabits(position, reversed)
        habitsMutableLiveData.value = arrayListOf()
    }

}