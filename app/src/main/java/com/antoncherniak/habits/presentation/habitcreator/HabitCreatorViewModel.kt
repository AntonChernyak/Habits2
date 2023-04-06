package com.antoncherniak.habits.presentation.habitcreator

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoncherniak.habits.domain.interactor.HabitCreatorInteractor
import com.antoncherniak.habits.domain.model.HabitModel
import com.antoncherniak.habits.presentation.habitcreator.HabitCreatorFragment.Companion.DEFAULT_ID
import kotlinx.coroutines.launch

class HabitCreatorViewModel(
    private val habitCreatorInteractor: HabitCreatorInteractor
) : ViewModel() {

    private val _resultHabitId: MutableLiveData<Int> = MutableLiveData()
    private val _snackbarMessage: MutableLiveData<Int> = MutableLiveData()
    val resultHabitId: LiveData<Int> = _resultHabitId
        // val snackbarMessage: LiveData<Int> = _snackbarMessage

    private fun habitCreator(
        habitOldId: Int,
        habit: HabitModel,
        addMessage: Int,
        updateMessage: Int
    ) {
        if (habitOldId == DEFAULT_ID) {
            addHabit(habit)
            _resultHabitId.value = DEFAULT_ID
            _snackbarMessage.value = addMessage
        } else {
            _resultHabitId.value = habitOldId
            _snackbarMessage.value = updateMessage
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

    fun createOrUpdateHabit(
        habitOldId: Int,
        habit: HabitModel,
        @StringRes addMessage: Int,
        @StringRes updateMessage: Int
    ) {
        viewModelScope.launch {
            habitCreator(habitOldId, habit, addMessage, updateMessage)
        }
    }

}