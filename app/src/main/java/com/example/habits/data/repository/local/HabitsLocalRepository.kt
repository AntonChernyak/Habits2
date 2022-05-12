package com.example.habits.data.repository.local

import androidx.lifecycle.LiveData
import com.example.habits.data.database.HabitDao
import com.example.habits.data.model_vo.HabitItem
import com.example.habits.domain.repository.HabitCreatorRepository
import com.example.habits.domain.repository.HabitsListLocalRepository

class HabitsLocalRepository(private val habitDao: HabitDao) : HabitsListLocalRepository,
    HabitCreatorRepository {

    override fun getHabits(): LiveData<List<HabitItem>> {
        return habitDao.getAllHabits()
    }

    override fun getSearchHabits(query: String): LiveData<List<HabitItem>> {
        return habitDao.getSearchHabits(query)
    }

    override fun getSortedHabitsByPriorityASC(): LiveData<List<HabitItem>> {
        return habitDao.getPrioritySortASC()
    }

    override fun getSortedHabitsByPriorityDESC(): LiveData<List<HabitItem>> {
        return habitDao.getPrioritySortDESC()
    }

    override fun getSortedHabitsByTitleASC(): LiveData<List<HabitItem>> {
        return habitDao.getPrioritySortDESC()
    }

    override fun getSortedHabitsByTitleDESC(): LiveData<List<HabitItem>> {
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

    override suspend fun setCheckForHabit(isChecked: Boolean, id: Int) {
        habitDao.updateCheck(isChecked, id)
    }

    override suspend fun saveAllHabits(habitsList: List<HabitItem>) {
        habitDao.saveAllHabits(habitsList)
    }

}