package com.example.habits.presentation.extension

import androidx.fragment.app.Fragment
import com.example.habits.data.database.HabitDao
import com.example.habits.data.network.HabitApiInterface
import com.example.habits.data.repository.local.HabitsLocalCreatorRepository
import com.example.habits.data.repository.local.HabitsLocalListRepository
import com.example.habits.data.repository.remote.HabitsRemoteCreatorRepository
import com.example.habits.data.repository.remote.HabitsRemoteListRepository
import com.example.habits.domain.usecase.HabitCreatorUseCase
import com.example.habits.domain.usecase.HabitsListUseCase
import com.example.habits.presentation.factory.HabitViewModelFactory


fun Fragment.factory(habitDao: HabitDao, habitApi: HabitApiInterface) = HabitViewModelFactory(
    HabitsListUseCase(HabitsLocalListRepository(habitDao), HabitsRemoteListRepository(habitApi)),
    HabitCreatorUseCase(HabitsLocalCreatorRepository(habitDao), HabitsRemoteCreatorRepository(habitApi))
)