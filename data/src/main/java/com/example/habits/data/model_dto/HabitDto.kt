package com.example.habits.data.model_dto

import kotlinx.serialization.SerialName

@Serializable
class HabitDto(
    val color: Int,
    val count: Int,
    val date: Int,
    val description: String,
    @SerialName("done_dates")
    val doneDates: List<Int>,
    val frequency: Int,
    val priority: Int,
    val title: String,
    val type: Int,
    val uid: String
)