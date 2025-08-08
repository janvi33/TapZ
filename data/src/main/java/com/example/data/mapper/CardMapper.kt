package com.example.data.mapper


import com.example.data.remote.dto.CharacterDto
import com.example.domain.model.Card

fun CharacterDto.toDomain(index: Int): Card {
    return Card(
        id = index,
        emoji = this.image, // use the URL or emoji string as "emoji"
        isMatched = false,
        isFaceUp = false
    )
}
