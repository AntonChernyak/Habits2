package com.example.habits

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.habits.data.database.HabitDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        instance = this

        initRoomDatabase(applicationContext)
    }

    @Synchronized
    private fun initRoomDatabase(context: Context): HabitDatabase {
        if (dataBaseInstance == null) {
            dataBaseInstance = Room.databaseBuilder(
                context,
                HabitDatabase::class.java,
                HABIT_DB_NAME
            )
                .allowMainThreadQueries()
                .build()
        }
        Log.d("TAGGGG", "initRoom")
        return dataBaseInstance!!
    }


    companion object {
        private const val HABIT_DB_NAME = "habits_db"

        var dataBaseInstance: HabitDatabase? = null

        var instance: App? = null
            private set
    }
}