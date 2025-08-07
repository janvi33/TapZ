package com.example.data.repository

import com.example.data.mapper.toDomain
import com.example.data.remote.api.DragonBallApiService
import com.example.data.remote.dto.CharacterResponse
import com.example.domain.model.Card
import com.example.domain.repository.CardRepository

class CardRepositoryImpl(
    private val api: DragonBallApiService
) : CardRepository {

    override suspend fun getAllCards(): List<Card> {
        return api.getCharacters().map { it.toDomain() }
    }

    override suspend fun getCard(card: Card): Card {
        return card // Optional logic
    }
}


