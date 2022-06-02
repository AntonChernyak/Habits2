package com.example.habits.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.habits.data.database.model_vo.HabitItem
import com.example.habits.data.database.model_vo.HabitType
import com.example.habits.data.database.model_vo.PriorityType
import com.example.habits.presentation.activity.MainActivity
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@Rule
var mainActivityActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class HabitDaoTest {

    @get:Rule
    val databaseRule = DatabaseRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainActivityActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun addHabit() {
        val newHabit = HabitItem(
            id = "111",
            title = "Сделать что-нибудь хорошее",
            description = "Бабушку через дорогу перевести, например!",
            priority = PriorityType.HIGH,
            type = HabitType.GOOD_HABIT,
            periodCount = "2",
            periodDays = "5"
        )

        runTest {
            val oldDbSize = databaseRule.appDatabase.getHabitDao().getAllHabits().count()
            databaseRule.appDatabase.getHabitDao().addHabit(newHabit)
            val newDbSize = databaseRule.appDatabase.getHabitDao().getAllHabits().count()

            assertEquals(oldDbSize + 1, newDbSize)

        }
    }
}