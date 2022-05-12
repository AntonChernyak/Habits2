package com.example.habits.data.database

import androidx.room.TypeConverter
import com.example.habits.data.model_vo.HabitType

class ListTypeConverter {
    @TypeConverter
    fun fromList(list: List<Int>): String = list.joinToString()

    @TypeConverter
    fun toList(string: String): List<Int> = string.filter { it != ','}.map { it.toString().toInt() }
}