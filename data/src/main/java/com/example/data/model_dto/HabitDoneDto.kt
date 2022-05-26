package com.example.data.model_dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class HabitDoneDto(
    val date: Int,
    @SerialName("habit_uid")
    val habitUid: String
)