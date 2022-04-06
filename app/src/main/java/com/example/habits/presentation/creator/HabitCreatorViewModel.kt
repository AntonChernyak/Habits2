package com.example.habits.presentation.creator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habits.data.model.HabitItem
import com.example.habits.domain.usecase.HabitCreatorUseCase

class HabitCreatorViewModel(val habitCreatorUseCase: HabitCreatorUseCase): ViewModel() {

    private val mutableHabitLiveData: MutableLiveData<HabitItem> = MutableLiveData()
    private val mutableSnackbarLiveData: MutableLiveData<Boolean> = MutableLiveData()

    val habitLiveData: LiveData<HabitItem> = mutableHabitLiveData
    val snackbarLiveData: LiveData<Boolean> = mutableSnackbarLiveData

    fun addHabit(habitItem: HabitItem){
        habitCreatorUseCase.addHabit(habitItem)
    }

    fun replaceHabit(habitItem: HabitItem){
        habitCreatorUseCase.replaceHabit(habitItem)
    }

    fun removeLastHabit(){
        habitCreatorUseCase.removeLastHabit()
    }

}