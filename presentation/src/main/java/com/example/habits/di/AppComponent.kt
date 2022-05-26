package com.example.habits.di

import com.example.habits.App
import com.example.habits.presentation.creator.HabitCreatorFragment
import com.example.habits.presentation.list.HabitsListFragment
import dagger.Component
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

@ExperimentalSerializationApi
@Singleton
@Component(modules = [PresentationModule::class, DomainModule::class, DataModule::class])
interface AppComponent {

    fun inject(app: App)
    fun inject(listFragment: HabitsListFragment)
    fun inject(creatorFragment: HabitCreatorFragment)
}