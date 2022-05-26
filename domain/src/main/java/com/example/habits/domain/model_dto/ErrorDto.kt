package com.example.habits.domain.model_dto

import kotlinx.serialization.Serializable

@Serializable
class ErrorDto(
    val code: Int,
    val message: String
)