package com.antoncherniak.habits.presentation.habitcreator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoncherniak.habits.domain.interactor.HabitCreatorInteractor
import com.antoncherniak.habits.domain.model.HabitModel
import kotlinx.coroutines.launch

class HabitCreatorViewModel(
    private val habitCreatorInteractor: HabitCreatorInteractor
) : ViewModel() {

    private val _resultHabitId: MutableLiveData<Int> = MutableLiveData()
    val resultHabitId: LiveData<Int> = _resultHabitId

    private fun habitCreator(habitOldId: Int, habit: HabitModel) {
        if (habitOldId == HabitCreatorFragment.DEFAULT_ID) {
            addHabit(habit)
            _resultHabitId.value = habit.id

        } else {
            _resultHabitId.value = habitOldId
            updateHabit(habit.copy(id = habitOldId))
        }
    }

    private fun addHabit(habitModel: HabitModel) {
        viewModelScope.launch {
            habitCreatorInteractor.addHabit(habitModel)
        }
    }

    fun removeHabit(habitId: Int) {
        viewModelScope.launch {
            habitCreatorInteractor.removeHabit(habitId)
        }
    }

    fun updateHabit(habitModel: HabitModel) {
        viewModelScope.launch {
            habitCreatorInteractor.updateHabit(habitModel)
        }
    }

    fun createOrUpdateHabit(habitOldId: Int, habit: HabitModel) {
        viewModelScope.launch {
            habitCreator(habitOldId, habit)
        }
    }

}