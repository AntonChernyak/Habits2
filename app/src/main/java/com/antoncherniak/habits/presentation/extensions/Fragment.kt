package com.antoncherniak.habits.presentation.extensions

import androidx.fragment.app.Fragment
import com.antoncherniak.habits.data.repository.MockRepository
import com.antoncherniak.habits.domain.interactor.HabitCreatorInteractor
import com.antoncherniak.habits.domain.interactor.HabitListInteractor
import com.antoncherniak.habits.presentation.factory.ViewModelFactory

fun Fragment.viewModelFactory() = ViewModelFactory(
    HabitListInteractor(MockRepository()),
    HabitCreatorInteractor(MockRepository())
)