package com.example.habits.di

import androidx.room.Room
import com.example.habits.App
import com.example.habits.data.database.HabitDao
import com.example.habits.data.database.HabitDatabase
import com.example.habits.data.network.HabitApiInterface
import com.example.habits.data.network.HabitAuthenticator
import com.example.habits.data.repository.local.HabitsLocalCreatorRepository
import com.example.habits.data.repository.local.HabitsLocalListRepository
import com.example.habits.data.repository.remote.HabitsRemoteCreatorRepository
import com.example.habits.data.repository.remote.HabitsRemoteListRepository
import com.example.habits.domain.repository.HabitCreatorLocalRepository
import com.example.habits.domain.repository.HabitCreatorRemoteRepository
import com.example.habits.domain.repository.HabitsListLocalRepository
import com.example.habits.domain.repository.HabitsListRemoteRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
class DataModule(private val appContext: App) {
    private val baseUrl = "https://droid-test-server.doubletapp.ru/api/"
    private val contentType = "application/json".toMediaType()
    private val habitDbName = "habits_db"

    @Provides
    @Singleton
    fun provideApp() = appContext

    // Network
    @Provides
    @Singleton
    fun provideClient(): OkHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .authenticator(HabitAuthenticator())
        .build()


    @Singleton
    @Provides
    fun provideApiClient(client: OkHttpClient): HabitApiInterface =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
            .create(HabitApiInterface::class.java)

    @Singleton
    @Provides
    fun provideRemoteCreatorRepository(api: HabitApiInterface): HabitCreatorRemoteRepository {
        return HabitsRemoteCreatorRepository(api)
    }

    @Singleton
    @Provides
    fun provideRemoteListRepository(api: HabitApiInterface): HabitsListRemoteRepository {
        return HabitsRemoteListRepository(api)
    }

    // Database
    @Singleton
    @Provides
    fun provideDb(appContext: App): HabitDatabase {
        return Room.databaseBuilder(
            appContext,
            HabitDatabase::class.java,
            habitDbName
        )
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: HabitDatabase): HabitDao {
        return db.getHabitDao()
    }

    @Singleton
    @Provides
    fun provideLocalListRepository(dao: HabitDao): HabitsListLocalRepository {
        return HabitsLocalListRepository(dao)
    }

    @Singleton
    @Provides
    fun provideLocalCreatorRepository(dao: HabitDao): HabitCreatorLocalRepository {
        return HabitsLocalCreatorRepository(dao)
    }
}