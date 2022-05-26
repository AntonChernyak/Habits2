package com.example.habits.data.database

import androidx.room.*
import com.example.habits.data.database.model_vo.HabitItem
import com.example.habits.data.database.model_vo.HabitItem.Companion.HABITS_TABLE_NAME
import kotlinx.coroutines.flow.Flow

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
    fun getSearchHabits(searchString: String): Flow<List<HabitItem>>

    @Query("SELECT * FROM $HABITS_TABLE_NAME ORDER BY priority ASC")
    fun getPrioritySortASC(): Flow<List<HabitItem>>

    @Query("SELECT * FROM $HABITS_TABLE_NAME ORDER BY priority DESC")
    fun getPrioritySortDESC(): Flow<List<HabitItem>>

    @Query("SELECT * FROM $HABITS_TABLE_NAME ORDER BY title ASC")
    fun getTitleSortASC(): Flow<List<HabitItem>>

    @Query("SELECT * FROM $HABITS_TABLE_NAME ORDER BY title DESC")
    fun getTitleSortDESC(): Flow<List<HabitItem>>

    @Query("SELECT * FROM $HABITS_TABLE_NAME")
    fun getAllHabits(): Flow<List<HabitItem>>

    @Query("SELECT * FROM $HABITS_TABLE_NAME WHERE id = :id")
    fun getHabitById(id: Int): Flow<HabitItem>
}