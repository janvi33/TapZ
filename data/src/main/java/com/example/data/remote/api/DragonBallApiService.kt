package com.example.data.remote.api

import com.example.data.remote.dto.CharacterDto
import retrofit2.http.GET

interface DragonBallApiService {
    @GET("characters")
    suspend fun getCharacters(): List<CharacterDto>

}

