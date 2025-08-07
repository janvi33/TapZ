package com.example.data.remote.api

import com.example.data.remote.dto.CharacterResponse
import retrofit2.http.GET

interface DragonBallApiService {

    @GET("characters")
    suspend fun getCharacters(): CharacterResponse
}
