package com.example.habits.data.model_dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class HabitDto(
    val color: Int,
    val count: Int,
    val date: Int,
    val description: String,
    @SerialName("done_dates")
    val doneDates: IntArray,
    val frequency: Int,
    val priority: IntArray,
    val title: String,
    val type: IntArray,
    val uid: String
)