package com.example.habits.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.habits.data.model.HabitItem
import com.example.habits.domain.repository.HabitsListRepository

class HabitsListUseCase(
    private val localRepository: HabitsListRepository,
    private val remoteRepository: HabitsListRepository
) {

    suspend fun removeHabit(habit: HabitItem) {
        localRepository.removeHabit(habit)
        remoteRepository.removeHabit(habit)
    }

    suspend fun setCheckForHabit(isChecked: Boolean, id: Int) {
        localRepository.setCheckForHabit(isChecked, id)
        remoteRepository.setCheckForHabit(isChecked, id)
    }

    fun getHabits(): LiveData<List<HabitItem>> {
        return localRepository.getHabits()
/*            val scope = CoroutineScope(Job())
            scope.launch(Dispatchers.IO) {
                localRepository.saveAllHabits(remoteList.value ?: arrayListOf())
                scope.cancel()
            }

        } else databaseList.value ?: arrayListOf()*/
    }

   fun getSearchHabits(query: String): LiveData<List<HabitItem>> {
        return localRepository.getSearchHabits(query)
    }


    fun getSortedHabits(position: Int, reversed: Boolean): LiveData<List<HabitItem>> {
        return when (position) {
            0 -> {
                if (reversed) localRepository.getSortedHabitsByPriorityASC()
                else localRepository.getSortedHabitsByPriorityDESC()
            }
            else -> {
                if (reversed) localRepository.getSortedHabitsByTitleASC()
                else localRepository.getSortedHabitsByTitleDESC()
            }
        }
    }




/*    fun getHabits(position: Int, reversed: Boolean, query: String): List<HabitItem> {
        val searchItems = if (query.isEmpty()) repository.getHabits()
        else {
            repository.getHabits().filter { it.title.uppercase().startsWith(query.uppercase()) }

        }
        return getSortedHabits(position, reversed, searchItems)

    }*/
}