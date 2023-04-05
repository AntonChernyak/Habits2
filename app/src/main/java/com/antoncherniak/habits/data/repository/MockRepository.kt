package com.antoncherniak.habits.data.repository

import android.graphics.Color
import com.antoncherniak.habits.domain.model.HabitModel
import com.antoncherniak.habits.domain.model.HabitType
import com.antoncherniak.habits.domain.model.PriorityType
import com.antoncherniak.habits.domain.repository.HabitCreatorRepositoryInterface
import com.antoncherniak.habits.domain.repository.HabitListRepositoryInterface


class MockRepository : HabitListRepositoryInterface, HabitCreatorRepositoryInterface {
    private var habits: MutableList<HabitModel> = mutableListOf()

    init {
        habits = createHabitsRepository()
    }

    override fun getHabits(): List<HabitModel> = habits
        .sortedByDescending { it.priority }
        .toMutableList()

    override fun addHabit(habit: HabitModel) {
        habits.add(habit)
    }

    override fun updateHabit(habit: HabitModel) {
        val index = habits.indexOfFirst { it.id == habit.id }
        habits[index] = habit
    }

    override fun removeHabit(habitId: Int) {
        val index = habits.indexOfFirst { it.id == habitId }
        if (index != -1) habits.removeAt(index)
    }

    private fun createHabitsRepository(): MutableList<HabitModel> {
        val initialList = mutableListOf<HabitModel>()
        HabitModel(
            id = 1,
            title = "Погладить кота1",
            priority = PriorityType.HIGH,
            periodTimes = "3",
            periodDays = "1",
            color = Color.RED
        ).apply { initialList.add(this) }

        HabitModel(
            id = 2,
            title = "Покормить кота2",
            description = "Лучше кормить кота, а то он будет злиться. А до этого лучше не доводить, ибо страшен кот в гневе!",
            priority = PriorityType.MEDIUM,
            periodTimes = "4",
            periodDays = "1",
            color = Color.MAGENTA
        ).apply { initialList.add(this) }

        HabitModel(
            id = 3,
            title = "Погладить кота3",
            priority = PriorityType.HIGH,
            periodTimes = "3",
            periodDays = "1"
        ).apply { initialList.add(this) }

        HabitModel(
            id = 4,
            title = "Покормить кота4",
            description = "Лучше кормить кота, а то он будет злиться. А до этого лучше не доводить, ибо страшен кот в гневе!",
            priority = PriorityType.MEDIUM,
            periodTimes = "4",
            periodDays = "1",
            color = Color.parseColor("#283593")
        ).apply { initialList.add(this) }

        HabitModel(
            id = 5,
            title = "Погладить кота5",
            priority = PriorityType.LOW,
            periodTimes = "3",
            periodDays = "1",
            color = Color.RED
        ).apply { initialList.add(this) }

        HabitModel(
            id = 6,
            title = "Покормить кота6",
            description = "Лучше кормить кота, а то он будет злиться. А до этого лучше не доводить, ибо страшен кот в гневе!",
            priority = PriorityType.HIGH,
            periodTimes = "4",
            periodDays = "1",
            color = Color.MAGENTA
        ).apply { initialList.add(this) }

        HabitModel(
            id = 7,
            title = "Погладить кота777",
            priority = PriorityType.MEDIUM,
            periodTimes = "3",
            periodDays = "1",
            color = Color.RED
        ).apply { initialList.add(this) }

        HabitModel(
            id = 8,
            title = "8Покормить кота Покормить кота Покормить кота Покормить кота Покормить кота",
            description = "Лучше кормить кота, а то он будет злиться. А до этого лучше не доводить, ибо страшен кот в гневе!",
            priority = PriorityType.HIGH,
            periodTimes = "4",
            periodDays = "1",
            color = Color.MAGENTA
        ).apply { initialList.add(this) }

        HabitModel(
            id = 9,
            title = "Погладить кота9",
            priority = PriorityType.HIGH,
            periodTimes = "3",
            periodDays = "1",
            color = Color.RED
        ).apply { initialList.add(this) }

        HabitModel(
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

        HabitModel(
            id = 11,
            title = "Погладить кота11",
            priority = PriorityType.HIGH,
            periodTimes = "3",
            periodDays = "1",
            color = Color.RED
        ).apply { initialList.add(this) }

        HabitModel(
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