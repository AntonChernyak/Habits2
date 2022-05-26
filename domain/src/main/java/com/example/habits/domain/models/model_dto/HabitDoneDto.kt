package com.example.habits.domain.models.model_dto

import kotlinx.serialization.SerialName

@Serializable
class HabitDoneDto(
    val date: Int,
    @SerialName("habit_uid")
    val habitUid: String
)