package com.example.habits.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.habits.data.model.HabitItem
import com.example.habits.data.model.HabitItem.Companion.HABITS_TABLE_NAME

@Dao
interface HabitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addHabit(habitItem: HabitItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllHabits(habits: List<HabitItem>)

    @Query("SELECT * FROM $HABITS_TABLE_NAME")
    fun getAllHabits(): LiveData<List<HabitItem>>

    @Delete
    fun deleteHabit(habitItem: HabitItem)

    @Query("DELETE FROM $HABITS_TABLE_NAME")
    fun deleteAll()

    @Query("SELECT * FROM $HABITS_TABLE_NAME WHERE id = :id")
    fun getHabitById(id: Int): LiveData<HabitItem>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateHabit(habitItem: HabitItem)

    @Query("UPDATE $HABITS_TABLE_NAME SET is_checked = :isChecked WHERE id =:id")
    fun updateCheck(isChecked: Boolean, id: Int)

    @Query("SELECT * FROM $HABITS_TABLE_NAME WHERE title LIKE '%' || :searchString || '%'")
    fun getSearchHabits(searchString: String): LiveData<List<HabitItem>>
}