package com.example.habits.data.extension

import androidx.fragment.app.Fragment
import com.example.habits.data.database.HabitDao
import com.example.habits.data.repository.local.HabitsLocalRepository
import com.example.habits.data.repository.remote.MockRepository
import com.example.habits.domain.usecase.HabitCreatorUseCase
import com.example.habits.domain.usecase.HabitsListUseCase
import com.example.habits.presentation.factory.HabitViewModelFactory


fun Fragment.factory(habitDao: HabitDao) = HabitViewModelFactory(
    HabitsListUseCase(HabitsLocalRepository(habitDao), MockRepository),
    HabitCreatorUseCase(HabitsLocalRepository(habitDao), MockRepository)
)