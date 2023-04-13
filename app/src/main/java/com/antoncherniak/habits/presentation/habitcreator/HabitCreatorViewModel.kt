package com.antoncherniak.habits.presentation.habitcreator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoncherniak.habits.domain.interactor.HabitCreatorInteractor
import com.antoncherniak.habits.domain.model.HabitModel
import com.antoncherniak.habits.domain.model.HabitType
import com.antoncherniak.habits.domain.model.PriorityType
import kotlinx.coroutines.launch

class HabitCreatorViewModel(
    private val habitCreatorInteractor: HabitCreatorInteractor
) : ViewModel() {

    private var currentHabitModel = HabitModel()
    private var initHabit = HabitModel()
    var isNewHabit = true


    private val _resultHabitId: MutableLiveData<Int> = MutableLiveData()
    val resultHabitId: LiveData<Int> = _resultHabitId

    fun getHabitById(habitId: Int) {
        viewModelScope.launch {
            currentHabitModel = habitCreatorInteractor.getHabitById(habitId)
            initHabit = currentHabitModel
        }
    }

    private fun habitCreator(
        habitOldId: Int
    ) {
        if (isNewHabit) {
            _resultHabitId.value = currentHabitModel.id
            addHabit(currentHabitModel)
        } else {
            _resultHabitId.value = habitOldId
            updateHabit(currentHabitModel.copy(id = habitOldId))
        }
    }

    private fun addHabit(habitModel: HabitModel) {
        viewModelScope.launch {
            habitCreatorInteractor.addHabit(habitModel)
        }
    }

    fun updateHabit(habitModel: HabitModel) {
        viewModelScope.launch {
            habitCreatorInteractor.updateHabit(habitModel)
        }
    }

    fun removeHabit(habitId: Int) {
        viewModelScope.launch {
            habitCreatorInteractor.removeHabit(habitId)
        }
    }

    fun createOrUpdateHabit(habitOldId: Int) {
        viewModelScope.launch {
            habitCreator(habitOldId)
        }
    }

    fun getCurrentHabit(): HabitModel = currentHabitModel

    fun setHabitId(id: Int) {
        currentHabitModel = currentHabitModel.copy(
            id = id
        )
    }

    fun setHabitTitle(title: String) {
        currentHabitModel = currentHabitModel.copy(
            title = title
        )
    }

    fun setHabitDescription(description: String) {
        currentHabitModel = currentHabitModel.copy(
            description = description
        )
    }

    fun setHabitPriority(priorityType: PriorityType) {
        currentHabitModel = currentHabitModel.copy(
            priority = priorityType
        )
    }

    fun setHabitType(habitType: HabitType) {
        currentHabitModel = currentHabitModel.copy(
            type = habitType
        )
    }

    fun setPeriodDays(periodDays: String) {
        currentHabitModel = currentHabitModel.copy(
            periodDays = periodDays
        )
    }

    fun setPeriodTimes(periodTimes: String) {
        currentHabitModel = currentHabitModel.copy(
            periodTimes = periodTimes
        )
    }

    fun setHabitColor(color: Int) {
        currentHabitModel = currentHabitModel.copy(
            color = color
        )
    }

    fun getInitHabit(): HabitModel {
        return initHabit
    }
}