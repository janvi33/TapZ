package com.example.data.di

import com.example.data.remote.api.DisneyApiService
import com.example.data.repository.CharacterRepositoryImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://api.disneyapi.dev/"

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun provideDisneyApiService(retrofit: Retrofit): DisneyApiService =
        retrofit.create(DisneyApiService::class.java)

    // -----------------------------------------------
    // 🔧 TEMPORARY HELPERS FOR TESTING WITHOUT HILT
    // -----------------------------------------------

    fun testProvideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun testProvideDisneyApi(): DisneyApiService =
        testProvideRetrofit().create(DisneyApiService::class.java)

    fun testProvideCharacterRepository(): CharacterRepositoryImpl =
        CharacterRepositoryImpl(testProvideDisneyApi())
}
