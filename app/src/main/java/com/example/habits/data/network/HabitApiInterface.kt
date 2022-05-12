package com.example.habits.data.network

import com.example.habits.data.model_dto.ErrorDto
import com.example.habits.data.model_dto.HabitDto
import com.example.habits.data.model_dto.HabitUidDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface HabitApiInterface {

    /**
     * МОЖЕТ ПРИЛЕТЕТЬ ОШИБКА, А НЕ ID
     */

    @GET("habit")
    suspend fun getHabits() : List<HabitDto>

    @PUT("habit")
    suspend fun putHabit() : HabitUidDto

    @DELETE("habit")
    suspend fun deleteHabit() : ErrorDto

    @POST("habit_done")
    suspend fun checkHabit() : ErrorDto


}