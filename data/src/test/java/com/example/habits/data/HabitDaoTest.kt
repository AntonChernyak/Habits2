package com.example.habits.data

import com.example.habits.data.database.model_vo.HabitItem
import com.example.habits.data.database.model_vo.HabitType
import com.example.habits.data.database.model_vo.PriorityType
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

//@Rule
//var mainActivityActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

@ExperimentalCoroutinesApi
class HabitDaoTest {

    @get:Rule
    val databaseRule = DatabaseRule()

    //@get:Rule
    //var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

   // @get:Rule
   // var mainActivityActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun addHabit() {
        coroutineRule.testDispatcher.runBlockingTest {
            val newHabit = HabitItem(
                id = "111",
                title = "Сделать что-нибудь хорошее",
                description = "Бабушку через дорогу перевести, например!",
                priority = PriorityType.HIGH,
                type = HabitType.GOOD_HABIT,
                periodCount = "2",
                periodDays = "5"
            )

            val oldDbSize = databaseRule.appDatabase.getHabitDao().getAllHabits().count()
            databaseRule.appDatabase.getHabitDao().addHabit(newHabit)
            val newDbSize = databaseRule.appDatabase.getHabitDao().getAllHabits().count()

            assertEquals(oldDbSize + 1, newDbSize)

        }
    }

    @Test
    fun deleteHabit() {
        coroutineRule.testDispatcher.runBlockingTest {
            val newHabit = HabitItem(
                id = "222",
                title = "Сделать что-нибудь хорошее",
                description = "Бабушку через дорогу перевести, например!",
                priority = PriorityType.HIGH,
                type = HabitType.GOOD_HABIT,
                periodCount = "2",
                periodDays = "5"
            )

            val oldDbSize = databaseRule.appDatabase.getHabitDao().getAllHabits().count()
            databaseRule.appDatabase.getHabitDao().addHabit(newHabit)
            val newDbSize = databaseRule.appDatabase.getHabitDao().getAllHabits().count()
            assertEquals(oldDbSize + 1, newDbSize)

            databaseRule.appDatabase.getHabitDao().deleteHabit(newHabit)
            val sizeAfterDelete = databaseRule.appDatabase.getHabitDao().getAllHabits().count()
            assertEquals(oldDbSize , sizeAfterDelete)

        }
    }

}