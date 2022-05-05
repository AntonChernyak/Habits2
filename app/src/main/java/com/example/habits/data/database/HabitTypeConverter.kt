package com.example.habits.data.database

import androidx.room.TypeConverter
import com.example.habits.data.model_dto.HabitType

class HabitTypeConverter {

    @TypeConverter
    fun fromHabitType(type: HabitType): Int = type.ordinal

    @TypeConverter
    fun toHabitType(ordinal: Int): HabitType = HabitType.values()[ordinal]
}