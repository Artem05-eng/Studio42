package com.example.studio42.data.repository

import android.accounts.NetworkErrorException
import android.content.SharedPreferences
import com.example.studio42.data.datasource.EmployerNetworkDataSource
import com.example.studio42.domain.entity.*
import com.example.studio42.domain.repository.Repository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class RepositoryImpl @Inject constructor(
    private val network: EmployerNetworkDataSource,
    private val shared: SharedPreferences
) : Repository {

    private companion object {
        const val LOCALE_KEY = "222"
    }

    override suspend fun getEmloyerType(): List<EmloyerType> = suspendCoroutine { continuation ->
        network.getDetails().enqueue(object : Callback<EmployerTypeWrapper<EmloyerType>> {
            override fun onResponse(
                call: Call<EmployerTypeWrapper<EmloyerType>>,
                response: Response<EmployerTypeWrapper<EmloyerType>>
            ) {
                if (response.isSuccessful) {
                    continuation.resume(response.body()?.employer_type as List<EmloyerType>)
                } else {
                    0
                    continuation.resumeWithException(NetworkErrorException())
                }
            }

            override fun onFailure(call: Call<EmployerTypeWrapper<EmloyerType>>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })
    }

    override suspend fun getEmployer(text: String, type: String?, flag: Boolean): EmployerFound =
        suspendCoroutine { continuation ->
            if (type != "") {
                network.getEmloyers(text, type!!, flag)
                    .enqueue(object : Callback<EmployerFound> {
                        override fun onResponse(
                            call: Call<EmployerFound>,
                            response: Response<EmployerFound>
                        ) {
                            if (response.isSuccessful) {
                                continuation.resume(
                                        response.body()!!
                                )
                            } else {
                                continuation.resumeWithException(NetworkErrorException())
                            }
                        }

                        override fun onFailure(
                            call: Call<EmployerFound>,
                            t: Throwable
                        ) {
                            continuation.resumeWithException(t)
                        }
                    })
            } else {
                network.getEmployersLight(text, flag)
                    .enqueue(object : Callback<EmployerFound> {
                        override fun onResponse(
                            call: Call<EmployerFound>,
                            response: Response<EmployerFound>
                        ) {
                            if (response.isSuccessful) {
                                continuation.resume(
                                        response.body()!!
                                )
                            } else {
                                continuation.resumeWithException(NetworkErrorException())
                            }
                        }

                        override fun onFailure(
                            call: Call<EmployerFound>,
                            t: Throwable
                        ) {
                            continuation.resumeWithException(t)
                        }
                    })
            }
        }

    override fun getDataFromShared(): List<EmloyerType> {
        val json = shared.getString(LOCALE_KEY, null)
        val listType = object : TypeToken<List<EmloyerType>>(){ }.type
        return Gson().fromJson<List<EmloyerType>>(json, listType)
    }

    override fun saveDataToShared(data: List<EmloyerType>) {
        val string = Gson().toJson(data)
        shared.edit().putString(LOCALE_KEY, string).apply()
    }
}