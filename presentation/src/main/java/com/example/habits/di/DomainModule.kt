package com.example.habits.di

import com.example.habits.domain.repository.HabitCreatorLocalRepository
import com.example.habits.domain.repository.HabitCreatorRemoteRepository
import com.example.habits.domain.repository.HabitsListLocalRepository
import com.example.habits.domain.repository.HabitsListRemoteRepository
import com.example.habits.domain.usecase.HabitCreatorInteractor
import com.example.habits.domain.usecase.HabitsListInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    @Singleton
    fun providesHabitCreatorInteractor(
        localRepository: HabitCreatorLocalRepository,
        remoteRepository: HabitCreatorRemoteRepository
    ): HabitCreatorInteractor {
        return HabitCreatorInteractor(localRepository, remoteRepository)
    }

    @Provides
    @Singleton
    fun providesHabitsListInteractor(
        localRepository: HabitsListLocalRepository,
        remoteRepository: HabitsListRemoteRepository
    ): HabitsListInteractor {
        return HabitsListInteractor(localRepository, remoteRepository)
    }
}
