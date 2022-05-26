package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.model_vo.HabitItem

@Database(entities = [HabitItem::class], version = 1)
@TypeConverters(HabitTypeConverter::class, HabitTypeConverter::class, ListTypeConverter::class)
abstract class HabitDatabase : RoomDatabase() {

    abstract fun getHabitDao(): HabitDao

/*    companion object {
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

    }*/
}