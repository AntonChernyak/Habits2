package com.example.habits.presentation.creator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habits.domain.models.model_dto.HabitUidDto
import com.example.habits.domain.models.model_vo.HabitItem
import com.example.habits.domain.usecase.HabitCreatorUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HabitCreatorViewModel(private val habitCreatorUseCase: HabitCreatorUseCase) : ViewModel() {

    fun addHabit(habitItem: HabitItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = habitCreatorUseCase.addHabit(habitItem)
        }
    }

    fun replaceHabit(habitItem: HabitItem) {
        viewModelScope.launch(Dispatchers.IO) {
            habitCreatorUseCase.replaceHabit(habitItem)
        }
    }

    fun removeHabit(habitItem: HabitItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val uid = HabitUidDto(habitItem.id)
            habitCreatorUseCase.removeHabit(habitItem, uid)
        }
    }

}