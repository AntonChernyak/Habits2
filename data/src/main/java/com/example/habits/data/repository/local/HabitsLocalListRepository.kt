package com.example.habits.data.repository.local

import com.example.habits.data.database.HabitDao
import com.example.habits.data.database.mapper.HabitMapper
import com.example.habits.domain.model_dto.HabitDto
import com.example.habits.domain.repository.HabitsListLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HabitsLocalListRepository(private val habitDao: HabitDao) : HabitsListLocalRepository{

    private val mapper = HabitMapper()

    override fun getHabits(): Flow<List<HabitDto>> {
        return habitDao.getAllHabits().map { mapper.toDataTransferObject(it) }
    }

    override fun getSearchHabits(query: String): Flow<List<HabitDto>> {
        return habitDao.getSearchHabits(query).map { mapper.toDataTransferObject(it)  }
    }

    override fun getSortedHabitsByPriorityASC(): Flow<List<HabitDto>> {
        return habitDao.getPrioritySortASC().map { mapper.toDataTransferObject(it)  }
    }

    override fun getSortedHabitsByPriorityDESC(): Flow<List<HabitDto>> {
        return habitDao.getPrioritySortDESC().map { mapper.toDataTransferObject(it)  }
    }

    override fun getSortedHabitsByTitleASC(): Flow<List<HabitDto>> {
        return habitDao.getPrioritySortDESC().map { mapper.toDataTransferObject(it)  }
    }

    override fun getSortedHabitsByTitleDESC(): Flow<List<HabitDto>> {
        return habitDao.getTitleSortDESC().map { mapper.toDataTransferObject(it)  }
    }
/*
    override suspend fun addHabit(habit: HabitDto) {
        habitDao.addHabit(mapper.toViewObject(habit))
    }

    override suspend fun replaceHabit(newHabit: HabitDto) {
        habitDao.updateHabit(mapper.toViewObject(newHabit))
    }

    override suspend fun removeHabit(habit: HabitDto) {
        habitDao.deleteHabit(mapper.toViewObject(habit))
    }*/

    override suspend fun setCheckForHabit(doneDates: List<Int>, id: String) {
        habitDao.updateCheck(doneDates, id)
    }

    override suspend fun saveAllHabits(habitsList: List<HabitDto>) {
        habitDao.saveAllHabits(mapper.toViewObject(habitsList))
    }

    override suspend fun removeHabit(habit: HabitDto) {
        habitDao.deleteHabit(mapper.toViewObject(habit))
    }
}