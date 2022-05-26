package com.example.habits.data.repository.local

import com.example.habits.data.database.HabitDao
import com.example.habits.data.database.model_vo.HabitItem
import com.example.habits.domain.repository.HabitCreatorLocalRepository
import com.example.habits.domain.repository.HabitsListLocalRepository
import kotlinx.coroutines.flow.Flow

class HabitsLocalRepository(private val habitDao: HabitDao) : HabitsListLocalRepository,
    HabitCreatorLocalRepository {

    override fun getHabits(): Flow<List<HabitItem>> {
        return habitDao.getAllHabits()
    }

    override fun getSearchHabits(query: String): Flow<List<HabitItem>> {
        return habitDao.getSearchHabits(query)
    }

    override fun getSortedHabitsByPriorityASC(): Flow<List<HabitItem>> {
        return habitDao.getPrioritySortASC()
    }

    override fun getSortedHabitsByPriorityDESC(): Flow<List<HabitItem>> {
        return habitDao.getPrioritySortDESC()
    }

    override fun getSortedHabitsByTitleASC(): Flow<List<HabitItem>> {
        return habitDao.getPrioritySortDESC()
    }

    override fun getSortedHabitsByTitleDESC(): Flow<List<HabitItem>> {
        return habitDao.getTitleSortDESC()
    }

    override suspend fun addHabit(habit: HabitItem) {
        habitDao.addHabit(habit)
    }

    override suspend fun replaceHabit(newHabit: HabitItem) {
        habitDao.updateHabit(newHabit)
    }

    override suspend fun removeHabit(habit: HabitItem) {
        habitDao.deleteHabit(habit)
    }

    override suspend fun setCheckForHabit(doneDates: List<Int>, id: String) {
        habitDao.updateCheck(doneDates, id)
    }

    override suspend fun saveAllHabits(habitsList: List<HabitItem>) {
        habitDao.saveAllHabits(habitsList)
    }

}