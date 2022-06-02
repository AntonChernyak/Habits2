package com.example.habits.di

import androidx.fragment.app.Fragment
import com.example.habits.presentation.creator.HabitCreatorFragment
import com.example.habits.presentation.list.HabitsListFragment
import dagger.BindsInstance
import dagger.Subcomponent
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@FragmentScope
@Subcomponent(modules = [PresentationModule::class])
interface FragmentViewModelComponent {

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun fragment(fragment: Fragment): Builder
        fun build(): FragmentViewModelComponent
    }

    fun inject(habitsListFragment: HabitsListFragment)
    fun inject(habitCreatorFragment: HabitCreatorFragment)
}
