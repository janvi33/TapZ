package com.example.data.repository

import com.example.data.mapper.toDomain
import com.example.data.remote.api.DragonBallApiService
import com.example.domain.model.Card
import com.example.domain.repository.CardRepository

class CardRepositoryImpl(
    private val api: DragonBallApiService
) : CardRepository {

    override suspend fun getAllCards(): List<Card> {
        val response = api.getCharacters(limit = 20)
        return response.items.map { it.toDomain() }
    }
}