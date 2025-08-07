package com.example.data

import com.example.data.remote.api.DisneyApiService
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun main() = runBlocking {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.disneyapi.dev/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    val api = retrofit.create(DisneyApiService::class.java)
    val response = api.getCharacters()

    // Print all image URLs
    response.data.forEach {
        println(it.imageUrl)
    }
}
