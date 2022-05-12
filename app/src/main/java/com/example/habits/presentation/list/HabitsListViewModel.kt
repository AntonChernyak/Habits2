package com.example.habits.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habits.data.model_dto.HabitDoneDto
import com.example.habits.data.model_vo.HabitItem
import com.example.habits.domain.usecase.HabitsListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HabitsListViewModel(
    private val habitsUseCase: HabitsListUseCase
) : ViewModel() {

    private val mutableHabitsLiveData = MutableLiveData<List<HabitItem>>()
    val habitsLiveData = mutableHabitsLiveData

    fun getHabits(): LiveData<List<HabitItem>> {
        return habitsUseCase.getHabits()
    }

    fun getHabitsFromNetwork(){
        viewModelScope.launch(Dispatchers.IO) {
            val habits = habitsUseCase.getHabitsFromNetwork()
            mutableHabitsLiveData.postValue(habits)
            habitsUseCase.saveAllHabits(habits)
        }
    }

    fun removeHabit(habitItem: HabitItem) {
        viewModelScope.launch(Dispatchers.IO) {
            habitsUseCase.removeHabit(habitItem)
        }
        getHabits()
    }

    fun setCheckForHabit(isChecked: Boolean, id: Int, habitDone: HabitDoneDto) {
        viewModelScope.launch(Dispatchers.IO) {
            habitsUseCase.setCheckForHabit(isChecked, id, habitDone)
        }
    }

    fun getSearchList(query: String): LiveData<List<HabitItem>> {
        return habitsUseCase.getSearchHabits(query)
    }

    fun getSortedHabits(position: Int, reversed: Boolean): LiveData<List<HabitItem>> {
         return habitsUseCase.getSortedHabits(position, reversed)
    }

}