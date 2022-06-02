package com.example.habits.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.habits.data.database.model_vo.HabitItem

@Database(entities = [HabitItem::class], version = 1)
@TypeConverters(HabitTypeConverter::class, HabitPriorityConverter::class, ListTypeConverter::class)
abstract class HabitDatabase : RoomDatabase() {

    abstract fun getHabitDao(): HabitDao
}