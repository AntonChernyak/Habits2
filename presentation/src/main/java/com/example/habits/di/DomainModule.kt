package com.example.habits.di

import com.example.habits.domain.repository.HabitCreatorLocalRepository
import com.example.habits.domain.repository.HabitCreatorRemoteRepository
import com.example.habits.domain.repository.HabitsListLocalRepository
import com.example.habits.domain.repository.HabitsListRemoteRepository
import com.example.habits.domain.usecase.HabitCreatorUseCase
import com.example.habits.domain.usecase.HabitsListUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    @Singleton
    fun providesHabitCreatorUseCase(
        localRepository: HabitCreatorLocalRepository,
        remoteRepository: HabitCreatorRemoteRepository
    ): HabitCreatorUseCase {
        return HabitCreatorUseCase(localRepository, remoteRepository)
    }

    @Provides
    @Singleton
    fun providesHabitsListUseCase(
        localRepository: HabitsListLocalRepository,
        remoteRepository: HabitsListRemoteRepository
    ): HabitsListUseCase {
        return HabitsListUseCase(localRepository, remoteRepository)
    }
}
