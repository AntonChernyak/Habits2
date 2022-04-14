package com.example.habits.data.database

import androidx.room.*
import com.example.habits.data.model.HabitItem
import com.example.habits.data.model.HabitItem.Companion.HABITS_TABLE_NAME

@Dao
interface HabitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addHabit(habitItem: HabitItem)

    @Query("SELECT * FROM $HABITS_TABLE_NAME")
    fun getAllHabits(): List<HabitItem>

    @Delete
    fun deleteHabit(habitItem: HabitItem)

    @Query("DELETE FROM $HABITS_TABLE_NAME")
    fun deleteAll()

    @Query("SELECT * FROM $HABITS_TABLE_NAME WHERE id = :id")
    fun getHabitById(id: Int): HabitItem

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateHabit(habitItem: HabitItem)

    @Query("SELECT * FROM $HABITS_TABLE_NAME ORDER BY priority")
    fun getHabitsOrderByPriority(): List<HabitItem>

    @Query("SELECT * FROM $HABITS_TABLE_NAME ORDER BY title")
    fun getHabitsOrderByTitle(): List<HabitItem>

    @Query("UPDATE $HABITS_TABLE_NAME SET is_checked = :isChecked WHERE id =:id")
    fun updateCheck(isChecked: Boolean, id: Int)
}