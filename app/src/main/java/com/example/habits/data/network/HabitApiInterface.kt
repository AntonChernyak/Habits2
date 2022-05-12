package com.example.habits.data.network

import com.example.habits.data.model_dto.ErrorDto
import com.example.habits.data.model_dto.HabitDoneDto
import com.example.habits.data.model_dto.HabitDto
import com.example.habits.data.model_dto.HabitUidDto
import retrofit2.http.*

interface HabitApiInterface {

    /**
     * МОЖЕТ ПРИЛЕТЕТЬ ОШИБКА, А НЕ ID
     */

    @GET("habit")
    suspend fun getHabits() : List<HabitDto>

    @PUT("habit")
    suspend fun putHabit(@Body habit: HabitDto) : HabitUidDto

    @DELETE("habit")
    suspend fun deleteHabit(@Body uid: String) : ErrorDto

    @POST("habit_done")
    suspend fun checkHabit(@Body habitDone: HabitDoneDto) : ErrorDto


}