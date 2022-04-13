package com.example.habits.data.extension

import androidx.fragment.app.Fragment
import com.example.habits.data.repository.MockRepository
import com.example.habits.domain.usecase.HabitCreatorUseCase
import com.example.habits.domain.usecase.HabitsListUseCase
import com.example.habits.presentation.factory.HabitViewModelFactory

fun Fragment.factory() = HabitViewModelFactory(
    HabitsListUseCase(MockRepository),
    HabitCreatorUseCase(MockRepository)
)