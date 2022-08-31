package com.example.studio42.domain.repository

import com.example.studio42.data.repository.RepositoryImpl
import com.example.studio42.domain.entity.EmloyerType
import com.example.studio42.domain.entity.Employer
import com.example.studio42.domain.entity.EmployerFound
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

interface Repository {

    suspend fun getEmloyerType(): List<EmloyerType>

    /*suspend fun getEmployer(text: String, type: String?, flag: Boolean): EmployerFound*/

    fun getDataFromShared(): List<EmloyerType>

    fun saveDataToShared(data: List<EmloyerType>)
}