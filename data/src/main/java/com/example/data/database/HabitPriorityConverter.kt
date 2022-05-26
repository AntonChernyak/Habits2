package com.example.data.database

import androidx.room.TypeConverter
import com.example.data.model_vo.PriorityType

class HabitPriorityConverter {

    @TypeConverter
    fun fromHabitPriority(type: PriorityType): Int = type.ordinal

    @TypeConverter
    fun toHabitPriority(ordinal: Int): PriorityType = PriorityType.values()[ordinal]
}