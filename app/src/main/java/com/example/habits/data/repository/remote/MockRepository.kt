package com.example.habits.data.repository.remote

import android.graphics.Color
import com.example.habits.data.model.HabitType
import com.example.habits.data.model.HabitItem
import com.example.habits.domain.repository.HabitCreatorRepository
import com.example.habits.domain.repository.HabitsListRepository

object MockRepository : HabitsListRepository, HabitCreatorRepository {
    private var habits: MutableList<HabitItem> = mutableListOf()

    init {
        habits = createHabitsRepository()
    }

    override fun getHabits(): List<HabitItem> {
        return habits
    }

    override fun removeHabit(habit: HabitItem) {
        val indexToDelete = habits.indexOfFirst { it.id == habit.id }
        if (indexToDelete != -1) {
            habits.removeAt(indexToDelete)
        }
    }

    override fun setCheckForHabit(isChecked: Boolean, id: Int) {
        val index = habits.indexOfFirst { it.id == id }
        if (index != -1) {
            habits[index].isChecked = !habits[index].isChecked

        }
    }

    override fun addHabit(habit: HabitItem) {
        habits.add(habit)
    }

    override fun replaceHabit(newHabit: HabitItem) {
        val index = habits.indexOfFirst { it.id == newHabit.id }
        if (index != -1) {
            habits[index] = newHabit
        }
    }

    private fun createHabitsRepository(): MutableList<HabitItem> {
        val initialList = mutableListOf<HabitItem>()
        HabitItem(
            id = 1,
            title = "Погладить кота",
            priority = "4",
            periodCount = "3",
            periodDays = "1",
            color = Color.RED
        ).apply { initialList.add(this) }

        HabitItem(
            id = 2,
            title = "Покормить кота",
            description = "Лучше кормить кота, а то он будет злиться. А до этого лучше не доводить, ибо страшен кот в гневе!",
            priority = "5",
            periodCount = "4",
            periodDays = "1",
            color = Color.MAGENTA
        ).apply { initialList.add(this) }

        HabitItem(
            id = 3,
            title = "Погладить кота",
            priority = "4",
            periodCount = "3",
            periodDays = "1"
        ).apply { initialList.add(this) }

        HabitItem(
            id = 4,
            title = "Покормить кота",
            description = "Лучше кормить кота, а то он будет злиться. А до этого лучше не доводить, ибо страшен кот в гневе!",
            priority = "5",
            periodCount = "4",
            periodDays = "1",
            isChecked = true,
            color = Color.parseColor("#283593")
        ).apply { initialList.add(this) }

        HabitItem(
            id = 5,
            title = "Погладить кота",
            priority = "4",
            periodCount = "3",
            periodDays = "1",
            color = Color.RED
        ).apply { initialList.add(this) }

        HabitItem(
            id = 6,
            title = "Покормить кота",
            description = "Лучше кормить кота, а то он будет злиться. А до этого лучше не доводить, ибо страшен кот в гневе!",
            priority = "1",
            periodCount = "4",
            periodDays = "1",
            color = Color.MAGENTA
        ).apply { initialList.add(this) }

        HabitItem(
            id = 7,
            title = "Погладить кота",
            priority = "4",
            periodCount = "3",
            periodDays = "1",
            color = Color.RED
        ).apply { initialList.add(this) }

        HabitItem(
            id = 8,
            title = "Покормить кота Покормить кота Покормить кота Покормить кота Покормить кота",
            description = "Лучше кормить кота, а то он будет злиться. А до этого лучше не доводить, ибо страшен кот в гневе!",
            priority = "5",
            periodCount = "4",
            periodDays = "1",
            color = Color.MAGENTA
        ).apply { initialList.add(this) }

        HabitItem(
            id = 9,
            title = "Погладить кота",
            priority = "4",
            periodCount = "3",
            periodDays = "1",
            color = Color.RED
        ).apply { initialList.add(this) }

        HabitItem(
            id = 10,
            title = "Покормить кота",
            description = "Лучше кормить кота, а то он будет злиться. А до этого лучше не доводить," +
                    " ибо страшен кот в гневе! Лучше кормить кота, а то он будет злиться. А до этого " +
                    "лучше не доводить, ибо страшен кот в гневе! Ещё текст выаоы щыо аыоаш щыоао ыщаоыщ оаыщвоа " +
                    "ыоашщ ыоашщоы щаоыв оаышо аышваошв ыоащ оащыо ащывоа щоыща ывщ",
            priority = "5",
            periodCount = "4",
            periodDays = "1",
            color = Color.MAGENTA
        ).apply { initialList.add(this) }

        HabitItem(
            id = 11,
            title = "Погладить кота",
            priority = "4",
            periodCount = "3",
            periodDays = "1",
            color = Color.RED
        ).apply { initialList.add(this) }

        HabitItem(
            id = 12,
            title = "Покормить кота",
            description = "Лучше кормить кота, а то он будет злиться. А до этого лучше не доводить, ибо страшен кот в гневе!",
            priority = "5",
            periodCount = "4",
            periodDays = "1",
            type = HabitType.BAD_HABIT,
            color = Color.MAGENTA
        ).apply { initialList.add(this) }

        return initialList
    }
}
