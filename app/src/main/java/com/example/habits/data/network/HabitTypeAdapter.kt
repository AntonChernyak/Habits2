package com.example.habits.data.network

import com.example.habits.data.model_dto.HabitDto
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class HabitTypeAdapter: TypeAdapter<HabitDto> () {
    override fun write(out: JsonWriter?, value: HabitDto?) {
        TODO("Not yet implemented")
    }

    override fun read(`in`: JsonReader?): HabitDto {
        TODO("Not yet implemented")
    }
}