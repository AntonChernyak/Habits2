package com.example.habits.repository

import android.graphics.Color
import com.example.habits.enum.HabitType
import com.example.habits.model.HabitItem

object MockRepository {
    private val list: MutableList<HabitItem> =
        createHabitsRepository().sortedBy { it.priority }.reversed().toMutableList()

    fun getHabits(): MutableList<HabitItem> = list.sortedBy { it.priority }.reversed().toMutableList()

    fun addHabit(position: Int = list.size, habit: HabitItem) {
        list.add(position, habit)
    }

    fun removeHabit(habit: HabitItem) {
        list.remove(habit)
    }

    fun removeLastHabit() {
        list.removeLast()
    }

    fun removeHabitAtPosition(position: Int){
        removeHabit(getHabits()[position])
    }

    private fun createHabitsRepository(): MutableList<HabitItem> {
        val initialList = mutableListOf<HabitItem>()
        HabitItem(
            title = "Погладить кота",
            priority = "4",
            periodCount = "3",
            periodDays = "1",
            color = Color.RED
        ).apply { initialList.add(this) }

        HabitItem(
            title = "Покормить кота",
            description = "Лучше кормить кота, а то он будет злиться. А до этого лучше не доводить, ибо страшен кот в гневе!",
            priority = "5",
            periodCount = "4",
            periodDays = "1",
            color = Color.MAGENTA
        ).apply { initialList.add(this) }

        HabitItem(
            title = "Погладить кота",
            priority = "4",
            periodCount = "3",
            periodDays = "1"
        ).apply { initialList.add(this) }

        HabitItem(
            title = "Покормить кота",
            description = "Лучше кормить кота, а то он будет злиться. А до этого лучше не доводить, ибо страшен кот в гневе!",
            priority = "5",
            periodCount = "4",
            periodDays = "1",
            isChecked = true,
            color = Color.parseColor("#283593")
        ).apply { initialList.add(this) }

        HabitItem(
            title = "Погладить кота",
            priority = "4",
            periodCount = "3",
            periodDays = "1",
            color = Color.RED
        ).apply { initialList.add(this) }

        HabitItem(
            title = "Покормить кота",
            description = "Лучше кормить кота, а то он будет злиться. А до этого лучше не доводить, ибо страшен кот в гневе!",
            priority = "1",
            periodCount = "4",
            periodDays = "1",
            color = Color.MAGENTA
        ).apply { initialList.add(this) }

        HabitItem(
            title = "Погладить кота",
            priority = "4",
            periodCount = "3",
            periodDays = "1",
            color = Color.RED
        ).apply { initialList.add(this) }

        HabitItem(
            title = "Покормить кота Покормить кота Покормить кота Покормить кота Покормить кота",
            description = "Лучше кормить кота, а то он будет злиться. А до этого лучше не доводить, ибо страшен кот в гневе!",
            priority = "5",
            periodCount = "4",
            periodDays = "1",
            color = Color.MAGENTA
        ).apply { initialList.add(this) }

        HabitItem(
            title = "Погладить кота",
            priority = "4",
            periodCount = "3",
            periodDays = "1",
            color = Color.RED
        ).apply { initialList.add(this) }

        HabitItem(
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
            title = "Погладить кота",
            priority = "4",
            periodCount = "3",
            periodDays = "1",
            color = Color.RED
        ).apply { initialList.add(this) }

        HabitItem(
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