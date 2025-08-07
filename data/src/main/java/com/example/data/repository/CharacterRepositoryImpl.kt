// data/repository/CharacterRepositoryImpl.kt
package com.example.data.repository

import com.example.data.remote.api.DragonBallApiService
import javax.inject.Inject

class CharacterRepositoryImpl(
    private val api: DragonBallApiService
) {
    suspend fun getCharacterImages(): List<String> {
        val response = api.getCharacters()
        return response.items.map { it.image }
    }
}


