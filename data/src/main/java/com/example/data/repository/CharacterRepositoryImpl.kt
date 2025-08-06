package com.example.data.repository

import com.example.data.remote.api.DisneyApiService
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: DisneyApiService
) {
    suspend fun getCharacterImages(): List<String> {
        return api.getCharacters().data.map { it.imageUrl }
    }
}
