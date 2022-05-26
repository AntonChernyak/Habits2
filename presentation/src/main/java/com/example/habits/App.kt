package com.example.habits

import android.app.Application
import com.example.habits.di.*
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class App : Application() {

    val component: AppComponent by lazy {
      DaggerAppComponent
            .builder()
            .presentationModule(PresentationModule())
            .domainModule(DomainModule())
            .dataModule(DataModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        component.inject(this)

        instance = this

    }
    companion object {
        var instance: App? = null
            private set
    }
}