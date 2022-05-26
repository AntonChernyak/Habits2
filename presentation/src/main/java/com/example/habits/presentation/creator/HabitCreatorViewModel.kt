package com.example.habits.presentation.creator

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habits.data.database.mapper.HabitMapper
import com.example.habits.domain.model_dto.HabitUidDto
import com.example.habits.data.database.model_vo.HabitItem
import com.example.habits.domain.usecase.HabitCreatorUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class HabitCreatorViewModel(private val habitCreatorUseCase: HabitCreatorUseCase) : ViewModel() {

    private val mapper = HabitMapper()

    fun addHabit(habitItem: HabitItem) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val id = habitCreatorUseCase.addHabit(
                    mapper.toDataTransferObject(habitItem)
                )
            } catch (e: Exception){
                Log.d("TAGGGGGG", "EX = ${e.message}")
            }

        }
    }

    fun replaceHabit(habitItem: HabitItem) {
        viewModelScope.launch(Dispatchers.IO) {
            habitCreatorUseCase.replaceHabit(
                mapper.toDataTransferObject(habitItem)
            )
        }
    }

    fun removeHabit(habitItem: HabitItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val uid = HabitUidDto(habitItem.id)
            habitCreatorUseCase.removeHabit(
                mapper.toDataTransferObject(habitItem),
                uid
            )
        }
    }

}