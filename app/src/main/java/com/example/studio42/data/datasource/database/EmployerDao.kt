package com.example.studio42.data.datasource.database

import androidx.paging.PagingSource
import androidx.room.*
import com.example.studio42.domain.entity.Employer

@Dao
interface EmployerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployers(users: List<Employer>)

    @Query("SELECT * FROM ${EmployerContract.TABLE_NAME_EMPLOYER} WHERE ${EmployerContract.CollumnEmployer.ID} = :id")
    suspend fun selectEmployerById(id: String): Employer

    @Query("SELECT * FROM ${EmployerContract.TABLE_NAME_EMPLOYER}")
    fun getPagingEmployers(): PagingSource<Int, Employer>

    @Query("SELECT * FROM ${EmployerContract.TABLE_NAME_EMPLOYER} WHERE ${EmployerContract.CollumnEmployer.ID} = :id")
    suspend fun getEmployerById(id: String) : Employer

    @Query("DELETE FROM ${EmployerContract.TABLE_NAME_EMPLOYER}")
    suspend fun clearEmployers()
}