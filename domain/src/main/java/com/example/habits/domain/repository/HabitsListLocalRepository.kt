package com.example.habits.domain.repository


import com.example.habits.domain.model_dto.HabitDto
import kotlinx.coroutines.flow.Flow

interface HabitsListLocalRepository {

    fun getHabits(): Flow<List<HabitDto>>

    fun getSearchHabits(query:String): Flow<List<HabitDto>>

    fun getSortedHabitsByPriorityASC(): Flow<List<HabitDto>>
    fun getSortedHabitsByPriorityDESC(): Flow<List<HabitDto>>

    fun getSortedHabitsByTitleASC(): Flow<List<HabitDto>>
    fun getSortedHabitsByTitleDESC(): Flow<List<HabitDto>>

    suspend fun saveAllHabits(habitsList: List<HabitDto>)

    suspend fun removeHabit(habit: HabitDto)

    suspend fun setCheckForHabit(doneDates: List<Int>, id: String)

}