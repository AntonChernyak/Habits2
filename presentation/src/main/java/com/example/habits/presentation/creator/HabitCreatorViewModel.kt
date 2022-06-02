package com.example.habits.presentation.creator

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habits.data.mapper.HabitMapper
import com.example.habits.domain.model_dto.HabitUidDto
import com.example.habits.data.database.model_vo.HabitItem
import com.example.habits.domain.usecase.HabitCreatorInteractor
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class HabitCreatorViewModel(
    private val habitCreatorInteractor: HabitCreatorInteractor
) : ViewModel() {

    private val mapper = HabitMapper()
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.message
    }

    fun addHabit(habitItem: HabitItem) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            try {
                val id = habitCreatorInteractor.addHabit(
                    mapper.toDataTransferObject(habitItem)
                )
            } catch (e: Exception) {
                Log.d("Creator_VM_TAG", "ADD EXCEPTION = ${e.message}")
            }

        }
    }

    fun replaceHabit(habitItem: HabitItem) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            habitCreatorInteractor.replaceHabit(
                mapper.toDataTransferObject(habitItem)
            )
        }
    }

    fun removeHabit(habitItem: HabitItem) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val uid = HabitUidDto(habitItem.id)
            habitCreatorInteractor.removeHabit(
                mapper.toDataTransferObject(habitItem),
                uid
            )
        }
    }

}