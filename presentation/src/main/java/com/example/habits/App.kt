package com.example.habits

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.habits.data.database.HabitDatabase
import com.example.habits.di.*

class App : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.create()
/*        DaggerAppComponent
            .builder()
            .presentationModule(PresentationModule())
            .domainModule(DomainModule())
            .dataModule(DataModule())
            .build()*/
    }

    override fun onCreate() {
        super.onCreate()

        component.inject(this)

        instance = this

      //  initRoomDatabase(applicationContext)
    }

/*    @Synchronized
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
        return dataBaseInstance!!
    }*/


    companion object {
      //  private const val HABIT_DB_NAME = "habits_db"

        //var dataBaseInstance: HabitDatabase? = null

        var instance: App? = null
            private set
    }
}