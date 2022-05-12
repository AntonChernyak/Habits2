package com.example.habits.data.database

import androidx.room.TypeConverter

class ListTypeConverter {
    @TypeConverter
    fun fromList(list: List<Int>): String = list.joinToString(separator = ", ", prefix = "[", postfix = "]")

    @TypeConverter
    fun toList(string: String): List<Int> = string.filter { it != ','}.map { it.toString().toInt() }
}