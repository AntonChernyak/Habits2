package com.example.habits.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.habits.domain.usecase.HabitCreatorInteractor
import com.example.habits.domain.usecase.HabitsListInteractor
import com.example.habits.presentation.creator.HabitCreatorViewModel
import com.example.habits.presentation.factory.HabitViewModelFactory
import com.example.habits.presentation.list.HabitsListViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Scope


@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentScope

@Module
class PresentationModule {

    @Provides
    @FragmentScope
    fun provideHabitListViewModel(
        habitListFragment: Fragment,
        habitsListInteractor: HabitsListInteractor,
        habitCreatorInteractor: HabitCreatorInteractor
    ): HabitsListViewModel {
        return ViewModelProvider(
            habitListFragment,
            HabitViewModelFactory(
                habitsListInteractor,
                habitCreatorInteractor
            )
        )[HabitsListViewModel::class.java]
    }

    @Provides
    @FragmentScope
    fun provideHabitCreatorViewModel(
        habitCreatorFragment: Fragment,
        habitsListInteractor: HabitsListInteractor,
        habitCreatorInteractor: HabitCreatorInteractor
    ): HabitCreatorViewModel {
        return ViewModelProvider(
            habitCreatorFragment,
            HabitViewModelFactory(
                habitsListInteractor,
                habitCreatorInteractor
            )
        )[HabitCreatorViewModel::class.java]
    }
}