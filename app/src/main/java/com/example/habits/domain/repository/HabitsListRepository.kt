package com.example.habits.domain.repository

import androidx.lifecycle.LiveData
import com.example.habits.data.model.HabitItem

interface HabitsListRepository {

    fun getHabits(): LiveData<List<HabitItem>>

    fun getSearchHabits(query:String): LiveData<List<HabitItem>>

    suspend fun saveAllHabits(habitsList: List<HabitItem>)

    suspend fun removeHabit(habit: HabitItem)

    suspend fun setCheckForHabit(isChecked: Boolean, id: Int)
}