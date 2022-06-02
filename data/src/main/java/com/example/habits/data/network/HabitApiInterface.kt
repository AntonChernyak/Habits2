package com.example.habits.data.network

import com.example.habits.domain.model_dto.HabitDoneDto
import com.example.habits.domain.model_dto.HabitDto
import com.example.habits.domain.model_dto.HabitUidDto
import retrofit2.http.*

interface HabitApiInterface {

    @GET("habit")
    suspend fun getHabits() : List<HabitDto>

    @PUT("habit")
    suspend fun putHabit(@Body habit: HabitDto) : HabitUidDto

    @HTTP(method = "DELETE", path = "habit", hasBody = true)
    suspend fun deleteHabit(@Body habitUidDto: HabitUidDto)

    @POST("habit_done")
    suspend fun checkHabit(@Body habitDone: HabitDoneDto)

}