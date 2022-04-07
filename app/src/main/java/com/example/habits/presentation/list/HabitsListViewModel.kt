package com.example.habits.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habits.data.model.HabitItem
import com.example.habits.domain.usecase.HabitsListUseCase

class HabitsListViewModel(private val habitsUseCase: HabitsListUseCase): ViewModel() {

    private val habitsMutableLiveData = MutableLiveData<List<HabitItem>>()

    val habitsLiveData: LiveData<List<HabitItem>> = habitsMutableLiveData

    fun getHabits(){
        val habits = habitsUseCase.getHabits()
        habitsMutableLiveData.value = habits
    }

    fun removeHabit(habitItem: HabitItem){
        habitsUseCase.removeHabit(habitItem)
        getHabits()
    }

    fun setCheckForHabit(habitItem: HabitItem){
        habitsUseCase.setCheckForHabit(habitItem)
    }

    fun getSearchList(query: String){
        if (query.length > 2) {
            val searchList = habitsUseCase.getSearchHabits(query)
            habitsMutableLiveData.value = searchList
        } else if (query.isEmpty()) getHabits()
    }

}