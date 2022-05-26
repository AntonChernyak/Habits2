package com.example.habits.di

import com.example.habits.App
import dagger.Component

@Component(modules = [PresentationModule::class, DomainModule::class, DataModule::class])
interface AppComponent {

    fun inject(app: App)
}