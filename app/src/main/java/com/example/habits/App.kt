package com.example.habits

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.habits.data.database.HabitDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        instance = this

        initRoomDatabase(applicationContext)
    }

    @Synchronized
    private fun initRoomDatabase(context: Context): com.example.habits.data.database.HabitDatabase {
        if (dataBaseInstance == null) {
            dataBaseInstance = Room.databaseBuilder(
                context,
                com.example.habits.data.database.HabitDatabase::class.java,
                HABIT_DB_NAME
            )
                .allowMainThreadQueries()
                .build()
        }
        return dataBaseInstance!!
    }


    companion object {
        private const val HABIT_DB_NAME = "habits_db"

        var dataBaseInstance: com.example.habits.data.database.HabitDatabase? = null

        var instance: App? = null
            private set
    }
}