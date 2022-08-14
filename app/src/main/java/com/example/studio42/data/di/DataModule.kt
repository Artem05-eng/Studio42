package com.example.studio42.data.di

import android.content.Context
import android.content.SharedPreferences
import com.example.studio42.app.App
import com.example.studio42.data.datasource.EmployerNetworkDataSource
import com.example.studio42.data.interceptor.CustomInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .addInterceptor(CustomInterceptor())
        .followRedirects(true)
        .build()

    @Singleton
    @Provides
    fun providesGson(): Gson = GsonBuilder().setLenient().create()

    @Singleton
    @Provides
    fun providesNetworkApi(okHttpClient: OkHttpClient, gson: Gson): EmployerNetworkDataSource =
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build().create()

    @Singleton
    @Provides
    fun providesSharedPrefs(app: App): SharedPreferences =
        app.getSharedPreferences(App.SHAREDPREFS_NAME, Context.MODE_PRIVATE)
}