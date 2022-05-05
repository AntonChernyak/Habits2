package com.example.habits.data.mapper

import com.example.habits.data.model_dto.HabitDto
import com.example.habits.data.model_vo.HabitItem
import com.example.habits.data.model_vo.HabitType
import com.example.habits.data.model_vo.PriorityType

class HabitMapper: ViewObjectMapper<HabitItem, HabitDto> {
    override fun toViewObject(dto: HabitDto): HabitItem {
        return HabitItem(
            id = dto.uid,
            title = dto.title,
            description = dto.description,
            priority = PriorityType.values()[dto.priority[0]],
            type = HabitType.values()[dto.type[0]],
            periodCount = dto.count.toString(),
            periodDays = dto.frequency.toString(),
            color = dto.color,
            dateOfCreation = dto.date.toLong(),
            isChecked = dto.doneDates.isNotEmpty()
        )
    }
}