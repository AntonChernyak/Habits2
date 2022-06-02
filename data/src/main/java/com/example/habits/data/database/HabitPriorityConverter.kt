package com.example.habits.data.database

import androidx.room.TypeConverter
import com.example.habits.data.database.model_vo.PriorityType

class HabitPriorityConverter {

    @TypeConverter
    fun fromHabitPriority(type: PriorityType): Int = type.ordinal

    @TypeConverter
    fun toHabitPriority(ordinal: Int): PriorityType = PriorityType.values()[ordinal]
}