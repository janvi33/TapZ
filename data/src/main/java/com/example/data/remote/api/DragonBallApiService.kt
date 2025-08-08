// data/remote/api/DragonBallApiService.kt
package com.example.data.remote.api

import com.example.data.remote.dto.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DragonBallApiService {
    @GET("characters")
    suspend fun getCharacters(
        @Query("limit") limit: Int = 20
    ): CharacterResponse
}
