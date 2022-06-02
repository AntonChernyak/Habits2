package com.example.habits.data.mapper

import com.example.habits.domain.model_dto.HabitDto
import com.example.habits.data.database.model_vo.HabitItem
import com.example.habits.data.database.model_vo.HabitType
import com.example.habits.data.database.model_vo.PriorityType

class HabitMapper : ViewObjectMapper<HabitItem, HabitDto> {
    override fun toViewObject(dto: HabitDto): HabitItem {
        return HabitItem(
            id = dto.uid,
            title = dto.title,
            description = dto.description,
            priority = PriorityType.values()[dto.priority],
            type = HabitType.values()[dto.type],
            periodCount = dto.count.toString(),
            periodDays = dto.frequency.toString(),
            color = dto.color,
            dateOfCreation = dto.date,
            doneDates = dto.doneDates.toList()
        )
    }

    override fun toDataTransferObject(vo: HabitItem): HabitDto {
        return HabitDto(
            color = vo.color,
            count = vo.periodCount.toInt(),
            date = vo.dateOfCreation,
            description = vo.description,
            doneDates = vo.doneDates.toList(),
            frequency = vo.periodDays.toInt(),
            priority = vo.priority.ordinal,
            title = vo.title,
            type = vo.type.ordinal,
            uid = vo.id
        )
    }
}