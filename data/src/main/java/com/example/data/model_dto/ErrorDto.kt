package com.example.data.model_dto

import kotlinx.serialization.Serializable

@Serializable
class ErrorDto(
    val code: Int,
    val message: String
)