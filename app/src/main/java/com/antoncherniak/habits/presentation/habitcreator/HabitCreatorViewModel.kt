package com.antoncherniak.habits.presentation.habitcreator

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoncherniak.habits.domain.interactor.HabitCreatorInteractor
import com.antoncherniak.habits.domain.model.HabitModel
import com.antoncherniak.habits.presentation.habitcreator.HabitCreatorFragment.Companion.DEFAULT_HABIT_ID
import kotlinx.coroutines.launch

class HabitCreatorViewModel(
    private val habitCreatorInteractor: HabitCreatorInteractor
) : ViewModel() {

    private val _resultHabitId: MutableLiveData<Int> = MutableLiveData()
    private val _snackbarMessage: MutableLiveData<Int> = MutableLiveData()
    val resultHabitId: LiveData<Int> = _resultHabitId
    // val snackbarMessage: LiveData<Int> = _snackbarMessage
    private var currentHabitModel = HabitModel()

    init {
        Log.e("TAAGGG", "INTI CREATOR VM")

    }

    private fun habitCreator(
        habitOldId: Int,
        habit: HabitModel,
        addMessage: Int,
        updateMessage: Int
    ) {
        if (habitOldId == DEFAULT_HABIT_ID) {
            addHabit(habit)
            _resultHabitId.value = DEFAULT_HABIT_ID
            _snackbarMessage.value = addMessage
        } else {
            _resultHabitId.value = habitOldId
            _snackbarMessage.value = updateMessage
            updateHabit(habit.copy(id = habitOldId))
        }
    }

    private fun addHabit(habitModel: HabitModel) {
        habitCreatorInteractor.addHabit(habitModel)
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

    fun getHabitById(habitId: Int) {
        viewModelScope.launch {
            currentHabitModel = habitCreatorInteractor.getHabitById(habitId)
        }
    }

    fun getCurrentHabit(): HabitModel = currentHabitModel

}