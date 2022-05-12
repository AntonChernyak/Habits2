package com.example.habits.domain.repository

import androidx.lifecycle.LiveData
import com.example.habits.data.model_vo.HabitItem

interface HabitsListLocalRepository {

    fun getHabits(): LiveData<List<HabitItem>>

    fun getSearchHabits(query:String): LiveData<List<HabitItem>>

    fun getSortedHabitsByPriorityASC(): LiveData<List<HabitItem>>
    fun getSortedHabitsByPriorityDESC(): LiveData<List<HabitItem>>

    fun getSortedHabitsByTitleASC(): LiveData<List<HabitItem>>
    fun getSortedHabitsByTitleDESC(): LiveData<List<HabitItem>>

    suspend fun saveAllHabits(habitsList: List<HabitItem>)

    suspend fun removeHabit(habit: HabitItem)

    suspend fun setCheckForHabit(isChecked: Boolean, id: Int)

}