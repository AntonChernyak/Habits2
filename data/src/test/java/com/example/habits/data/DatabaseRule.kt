package com.example.habits.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.habits.data.database.HabitDatabase
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith

//@RunWith(AndroidJUnit4::class)
class DatabaseRule: TestWatcher() {

    lateinit var appDatabase: HabitDatabase

    override fun starting(description: Description) {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            HabitDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    override fun finished(description: Description) {
        appDatabase.close()
    }
}