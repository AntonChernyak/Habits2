package com.antoncherniak.habits.data.repository

import android.graphics.Color
import com.antoncherniak.habits.domain.model.HabitModel
import com.antoncherniak.habits.domain.model.HabitType
import com.antoncherniak.habits.domain.model.PriorityType
import com.antoncherniak.habits.domain.repository.HabitCreatorRepositoryInterface
import com.antoncherniak.habits.domain.repository.HabitListRepositoryInterface


object MockRepository : HabitListRepositoryInterface, HabitCreatorRepositoryInterface {

    override fun getHabits(): List<HabitModel> = habits

    override fun addHabit(habit: HabitModel) {
        habits.add(habit)
    }

    override fun updateHabit(habit: HabitModel) {
        val index = habits.indexOfFirst { it.id == habit.id }
        habits[index] = habit
    }

    override fun getHabitById(habitId: Int): HabitModel {
        return getHabits().first { it.id == habitId }
    }

    override fun removeHabit(habitId: Int) {
        val index = habits.indexOfFirst { it.id == habitId }
        if (index != -1) habits.removeAt(index)
    }

    private val habits: MutableList<HabitModel> = mutableListOf(
        HabitModel(
            id = 1,
            title = "Погладить кота1",
            priority = PriorityType.HIGH,
            periodTimes = "3",
            periodDays = "1",
            color = Color.RED
        ),
        HabitModel(
            id = 2,
            title = "Покормить кота2",
            description = "Лучше кормить кота, а то он будет злиться. А до этого лучше не доводить, ибо страшен кот в гневе!",
            priority = PriorityType.MEDIUM,
            periodTimes = "4",
            periodDays = "1",
            color = Color.MAGENTA
        ),

        HabitModel(
            id = 3,
            title = "Погладить кота3",
            priority = PriorityType.HIGH,
            periodTimes = "3",
            periodDays = "1"
        ),

        HabitModel(
            id = 4,
            title = "Покормить кота4",
            description = "Лучше кормить кота, а то он будет злиться. А до этого лучше не доводить, ибо страшен кот в гневе!",
            priority = PriorityType.MEDIUM,
            periodTimes = "4",
            periodDays = "1",
            color = Color.parseColor("#283593")
        ),

        HabitModel(
            id = 5,
            title = "Погладить кота5",
            priority = PriorityType.LOW,
            periodTimes = "3",
            periodDays = "1",
            color = Color.RED
        ),

        HabitModel(
            id = 6,
            title = "Покормить кота6",
            description = "Лучше кормить кота, а то он будет злиться. А до этого лучше не доводить, ибо страшен кот в гневе!",
            priority = PriorityType.HIGH,
            periodTimes = "4",
            periodDays = "1",
            color = Color.MAGENTA
        ),

        HabitModel(
            id = 7,
            title = "Погладить кота777",
            priority = PriorityType.MEDIUM,
            periodTimes = "3",
            periodDays = "1",
            color = Color.RED
        ),

        HabitModel(
            id = 8,
            title = "8Покормить кота Покормить кота Покормить кота Покормить кота Покормить кота",
            description = "Лучше кормить кота, а то он будет злиться. А до этого лучше не доводить, ибо страшен кот в гневе!",
            priority = PriorityType.HIGH,
            periodTimes = "4",
            periodDays = "1",
            color = Color.MAGENTA
        ),
        HabitModel(
            id = 9,
            title = "Погладить кота9",
            priority = PriorityType.HIGH,
            periodTimes = "3",
            periodDays = "1",
            color = Color.RED
        ),

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
        ),
        HabitModel(
            id = 11,
            title = "Погладить кота11",
            priority = PriorityType.HIGH,
            periodTimes = "3",
            periodDays = "1",
            color = Color.RED
        ),
        HabitModel(
            id = 12,
            title = "Покормить кота12",
            description = "Лучше кормить кота, а то он будет злиться. А до этого лучше не доводить, ибо страшен кот в гневе!",
            priority = PriorityType.MEDIUM,
            periodTimes = "4",
            periodDays = "1",
            type = HabitType.BAD_HABIT,
            color = Color.MAGENTA
        ),
        HabitModel(
            id = 13,
            title = "Покормить собаку13",
            description = "Собака",
            priority = PriorityType.MEDIUM,
            periodTimes = "4",
            periodDays = "1",
            type = HabitType.BAD_HABIT,
            color = Color.MAGENTA
        ),
        HabitModel(
            id = 14,
            title = "Покормить собаку14",
            description = "страшен кот в гневе!",
            priority = PriorityType.MEDIUM,
            periodTimes = "4",
            periodDays = "1",
            type = HabitType.GOOD_HABIT,
            color = Color.MAGENTA
        ),
        HabitModel(
            id = 15,
            title = "Покормить собаку15",
            description = "",
            priority = PriorityType.HIGH,
            periodTimes = "4",
            periodDays = "1",
            type = HabitType.BAD_HABIT,
            color = Color.MAGENTA
        ),
        HabitModel(
            id = 16,
            title = "Покормить собаку16",
            description = "Лучше не кормить кота",
            priority = PriorityType.MEDIUM,
            periodTimes = "4",
            periodDays = "1",
            type = HabitType.BAD_HABIT,
            color = Color.MAGENTA
        ),
        HabitModel(
            id = 17,
            title = "Покормить собаку17",
            description = "Лучше кормить собаку!",
            priority = PriorityType.LOW,
            periodTimes = "4",
            periodDays = "1",
            type = HabitType.GOOD_HABIT,
            color = Color.MAGENTA
        )
    )
}