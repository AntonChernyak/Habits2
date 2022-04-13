package com.example.habits.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.habits.data.model.HabitItem

@Database(entities = [HabitItem::class], version = 1)
abstract class HabitDatabase : RoomDatabase() {

    abstract fun getHabitDao(): HabitDao

    companion object {
        private const val HABIT_DB_NAME = "habits_db"
        private var instance: HabitDatabase? = null

        @Synchronized
        fun get(context: Context): HabitDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    HabitDatabase::class.java,
                    HABIT_DB_NAME
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }

    }
}