package com.example.habits.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Module
class PresentationModule {

/*    @Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER
    )
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    @MapKey
    internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

    @Singleton
    class ViewModelFactory @Inject constructor(
        private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) :
        ViewModelProvider.Factory {

        *//**
         * 2 - Т.е. viewModels - это HashMap, в метод create мы передаём класс, который использует его как ключ и
         *  возвращает нам VM по этому ключу
         *//*
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            viewModels[modelClass]?.get() as T
    }

    *//**
     * 1 - описываем модуль для ViewModel, который и будет создавать нашу вьюмодельку
     *//*
    @Module
    abstract class ViewModelModule {

        @Binds
        abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

        @Binds
        @IntoMap
        @ViewModelKey(MainViewModel::class)
        abstract fun mainViewModel(viewModel: MainViewModel): ViewModel

    }*/

}