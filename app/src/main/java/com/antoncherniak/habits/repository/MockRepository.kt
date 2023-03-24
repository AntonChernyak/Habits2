package com.antoncherniak.habits.repository

import android.graphics.Color
import com.antoncherniak.habits.model.Habit
import com.antoncherniak.habits.model.HabitType
import com.antoncherniak.habits.model.PriorityType


class MockRepository {
    private var habits: MutableList<Habit> = mutableListOf()

    init {
        habits = createHabitsRepository()
    }

    fun getHabits(): List<Habit> = habits.sortedBy { it.priority }.reversed().toMutableList()

    fun addHabit(position: Int = habits.size, habit: Habit) {
        habits.add(position, habit)
    }

    fun removeHabitAtPosition(position: Int) {
        removeHabit(getHabits()[position])
    }

    fun replaceHabit(newHabit: Habit){
        val index = habits.indexOfFirst { it.id == newHabit.id }
        habits[index] = newHabit
    }

    fun removeLastHabit(){
        habits.removeLast()
    }

    private fun removeHabit(habit: Habit) {
        val indexToDelete = habits.indexOfFirst { it.id == habit.id }
        if (indexToDelete != -1) {
            habits.removeAt(indexToDelete)
        }
    }

    private fun createHabitsRepository(): MutableList<Habit> {
        val initialList = mutableListOf<Habit>()
        Habit(
            id = 1,
            title = "Погладить кота1",
            priority = PriorityType.HIGH,
            periodTimes = "3",
            periodDays = "1",
            color = Color.RED
        ).apply { initialList.add(this) }

        Habit(
            id = 2,
            title = "Покормить кота2",
            description = "Лучше кормить кота, а то он будет злиться. А до этого лучше не доводить, ибо страшен кот в гневе!",
            priority = PriorityType.MEDIUM,
            periodTimes = "4",
            periodDays = "1",
            color = Color.MAGENTA
        ).apply { initialList.add(this) }

        Habit(
            id = 3,
            title = "Погладить кота3",
            priority = PriorityType.HIGH,
            periodTimes = "3",
            periodDays = "1"
        ).apply { initialList.add(this) }

        Habit(
            id = 4,
            title = "Покормить кота4",
            description = "Лучше кормить кота, а то он будет злиться. А до этого лучше не доводить, ибо страшен кот в гневе!",
            priority = PriorityType.MEDIUM,
            periodTimes = "4",
            periodDays = "1",
            color = Color.parseColor("#283593")
        ).apply { initialList.add(this) }

        Habit(
            id = 5,
            title = "Погладить кота5",
            priority = PriorityType.LOW,
            periodTimes = "3",
            periodDays = "1",
            color = Color.RED
        ).apply { initialList.add(this) }

        Habit(
            id = 6,
            title = "Покормить кота6",
            description = "Лучше кормить кота, а то он будет злиться. А до этого лучше не доводить, ибо страшен кот в гневе!",
            priority = PriorityType.HIGH,
            periodTimes = "4",
            periodDays = "1",
            color = Color.MAGENTA
        ).apply { initialList.add(this) }

        Habit(
            id = 7,
            title = "Погладить кота777",
            priority = PriorityType.MEDIUM,
            periodTimes = "3",
            periodDays = "1",
            color = Color.RED
        ).apply { initialList.add(this) }

        Habit(
            id = 8,
            title = "8Покормить кота Покормить кота Покормить кота Покормить кота Покормить кота",
            description = "Лучше кормить кота, а то он будет злиться. А до этого лучше не доводить, ибо страшен кот в гневе!",
            priority = PriorityType.HIGH,
            periodTimes = "4",
            periodDays = "1",
            color = Color.MAGENTA
        ).apply { initialList.add(this) }

        Habit(
            id = 9,
            title = "Погладить кота9",
            priority = PriorityType.HIGH,
            periodTimes = "3",
            periodDays = "1",
            color = Color.RED
        ).apply { initialList.add(this) }

        Habit(
            id = 10,
            title = "Покормить кота10",
            description = "Лучше кормить кота, а то он будет злиться. А до этого лучше не доводить," +
                    " ибо страшен кот в гневе! Лучше кормить кота, а то он будет злиться. А до этого " +
                    "лучше не доводить, ибо страшен кот в гневе! Ещё текст выаоы щыо аыоаш щыоао ыщаоыщ оаыщвоа " +
                    "ыоашщ ыоашщоы щаоыв оаышо аышваошв ыоащ оащыо ащывоа щоыща ывщ",
            priority = PriorityType.LOW,
            periodTimes = "4",
            periodDays = "1",
            color = Color.MAGENTA
        ).apply { initialList.add(this) }

        Habit(
            id = 11,
            title = "Погладить кота11",
            priority = PriorityType.HIGH,
            periodTimes = "3",
            periodDays = "1",
            color = Color.RED
        ).apply { initialList.add(this) }

        Habit(
            id = 12,
            title = "Покормить кота12",
            description = "Лучше кормить кота, а то он будет злиться. А до этого лучше не доводить, ибо страшен кот в гневе!",
            priority = PriorityType.MEDIUM,
            periodTimes = "4",
            periodDays = "1",
            type = HabitType.BAD_HABIT,
            color = Color.MAGENTA
        ).apply { initialList.add(this) }

        return initialList
    }
}