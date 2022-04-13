package com.example.habits.data.database

import androidx.room.*
import com.example.habits.data.model.HabitItem

@Dao
interface HabitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addHabit(habitItem: HabitItem)

    @Query("SELECT * FROM habit_db")
    fun getAllHabits(): List<HabitItem>

    @Delete
    fun deleteHabit(habitItem: HabitItem)

    @Query("DELETE FROM habit_db")
    fun deleteAll()

    @Query("SELECT * FROM habit_db WHERE id = :id")
    fun getHabitById(id: Int): HabitItem

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateHabit(habitItem: HabitItem)

    @Query("SELECT * FROM habit_db ORDER BY priority")
    fun getHabitsOrderByPriority(): List<HabitItem>

    @Query("SELECT * FROM habit_db ORDER BY title")
    fun getHabitsOrderByTitle(): List<HabitItem>
}