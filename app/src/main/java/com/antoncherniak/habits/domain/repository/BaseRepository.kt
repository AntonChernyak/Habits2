package com.antoncherniak.habits.domain.repository


interface BaseRepository {

    fun removeHabit(habitId: Int)
}