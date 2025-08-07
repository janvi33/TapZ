package com.example.domain.usecase.card

import com.example.domain.model.Card
import com.example.domain.repository.CardRepository

class GetCardUseCase(val cardRepository: CardRepository) {
    suspend operator fun invoke(card: Card): Card {
        return cardRepository.getCard(card)
    }
}