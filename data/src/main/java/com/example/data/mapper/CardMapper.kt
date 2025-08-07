package com.example.data.mapper

import com.example.data.remote.dto.CharacterDto
import com.example.domain.model.Card

fun CharacterDto.toDomain(): Card {
    return Card(
        suit = null,       // Not available from API
        value = "N/A",     // Required by domain model
        image = image
    )
}
