package com.example.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.data.model_vo.HabitItem
import com.example.data.model_vo.HabitItem.Companion.HABITS_TABLE_NAME

@Dao
interface HabitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHabit(habitItem: HabitItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllHabits(habits: List<HabitItem>)

    @Delete
    suspend fun deleteHabit(habitItem: HabitItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateHabit(habitItem: HabitItem)

    @Query("UPDATE $HABITS_TABLE_NAME SET done_dates = :doneDates WHERE id =:id")
    suspend fun updateCheck(doneDates: List<Int>, id: String)

    @Query("SELECT * FROM $HABITS_TABLE_NAME WHERE title LIKE '%' || :searchString || '%'")
    fun getSearchHabits(searchString: String): LiveData<List<HabitItem>>

    @Query("SELECT * FROM $HABITS_TABLE_NAME ORDER BY priority ASC")
    fun getPrioritySortASC(): LiveData<List<HabitItem>>

    @Query("SELECT * FROM $HABITS_TABLE_NAME ORDER BY priority DESC")
    fun getPrioritySortDESC(): LiveData<List<HabitItem>>

    @Query("SELECT * FROM $HABITS_TABLE_NAME ORDER BY title ASC")
    fun getTitleSortASC(): LiveData<List<HabitItem>>

    @Query("SELECT * FROM $HABITS_TABLE_NAME ORDER BY title DESC")
    fun getTitleSortDESC(): LiveData<List<HabitItem>>

    @Query("SELECT * FROM $HABITS_TABLE_NAME")
    fun getAllHabits(): LiveData<List<HabitItem>>

    @Query("SELECT * FROM $HABITS_TABLE_NAME WHERE id = :id")
    fun getHabitById(id: Int): LiveData<HabitItem>
}