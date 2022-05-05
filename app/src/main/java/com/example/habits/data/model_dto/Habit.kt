package com.example.habits.data.model_dto

class Habit(
    val color: Int,
    val count: Int,
    val date: Int,
    val description: String,
    val done_dates: IntArray,
    val frequency: Int,
    val priority: IntArray,
    val title: String,
    val type: IntArray,
    val uid: String
)