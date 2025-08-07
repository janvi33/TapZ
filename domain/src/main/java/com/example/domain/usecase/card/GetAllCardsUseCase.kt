package com.example.domain.usecase.card

import com.example.domain.model.Card
import com.example.domain.repository.CardRepository

class GetAllCardsUseCase(val cardRepository: CardRepository) {
    suspend operator fun invoke(): List<Card> {
        return cardRepository.getAllCards()
    }
}