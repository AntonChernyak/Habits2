package com.example.data.database

import androidx.room.TypeConverter
import com.example.data.model_vo.HabitType

class HabitTypeConverter {

    @TypeConverter
    fun fromHabitType(type: HabitType): Int = type.ordinal

    @TypeConverter
    fun toHabitType(ordinal: Int): HabitType = HabitType.values()[ordinal]
}