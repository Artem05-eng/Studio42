package com.example.studio42.data.datasource

import com.example.studio42.domain.entity.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EmployerNetworkDataSource {

    @GET("/employers")
    fun getEmloyers(
        @Query("text") text: String,
        @Query("type", encoded = true) type: String,
        @Query("only_with_vacancies") only_with_vacancies: Boolean,
        @Query("per_page") per_page: Number = 100,
    ): Call<EmployerFound>

    @GET("/employers")
    fun getEmployersLight(
        @Query("text") text: String,
        @Query("only_with_vacancies") only_with_vacancies: Boolean,
        @Query("per_page") per_page: Number = 100
    ): Call<EmployerFound>

    @GET("/dictionaries")
    fun getDetails(): Call<EmployerTypeWrapper<EmloyerType>>
}