package com.antoncherniak.habits.presentation.extensions

import androidx.fragment.app.Fragment
import com.antoncherniak.habits.domain.interactor.HabitListInteractor
import com.antoncherniak.habits.domain.repository.HabitListRepositoryInterface
import com.antoncherniak.habits.presentation.factory.ViewModelFactory

fun Fragment.viewModelFactory(habitListRepository: HabitListRepositoryInterface) =
    ViewModelFactory(
        HabitListInteractor(habitListRepository)
    )