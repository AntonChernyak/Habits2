package com.example.habits.presentation.list

import androidx.lifecycle.*
import com.example.habits.data.database.mapper.HabitMapper
import com.example.habits.domain.model_dto.HabitDoneDto
import com.example.habits.domain.model_dto.HabitUidDto
import com.example.habits.data.database.model_vo.HabitItem
import com.example.habits.domain.usecase.HabitsListUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class HabitsListViewModel @Inject constructor(
    val habitsUseCase: HabitsListUseCase
) : ViewModel() {

    private val habitMapper = HabitMapper()
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.message
    }
    private val mutableHabitsLiveData = MutableLiveData<List<HabitItem>>()

    val habitsLiveData: LiveData<List<HabitItem>> = mutableHabitsLiveData

    fun getHabits(): LiveData<List<HabitItem>> {
        return habitsUseCase.getHabits().map { habitMapper.toViewObject(it) }.asLiveData()
    }

    fun getHabitsFromNetwork(){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val habits =habitMapper.toViewObject(habitsUseCase.getHabitsFromNetwork())
            mutableHabitsLiveData.postValue(habits)
            habitsUseCase.saveAllHabits(habitMapper.toDataTransferObject(habits))
        }
    }

    fun removeHabit(habitItem: HabitItem) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val uid = HabitUidDto(habitItem.id)
            habitsUseCase.removeHabit(
                habitMapper.toDataTransferObject(habitItem),
                uid
            )
        }
        getHabits()
    }

    fun setCheckForHabit(doneDates: List<Int>, habitDone: HabitDoneDto) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            habitsUseCase.setCheckForHabit(habitDone, doneDates)
        }
    }

    fun getSearchList(query: String): LiveData<List<HabitItem>> {
        return habitsUseCase.getSearchHabits(query)
            .map { habitMapper.toViewObject(it) }.asLiveData()
    }

    fun getSortedHabits(position: Int, reversed: Boolean): LiveData<List<HabitItem>> {
         return habitsUseCase.getSortedHabits(position, reversed)
             .map { habitMapper.toViewObject(it) }.asLiveData()
    }

}