// data/mapper/CardMapper.kt
package com.example.data.mapper

import com.example.data.remote.dto.CharacterDto
import com.example.domain.model.Card

private fun normalizeImage(url: String?): String {
    if (url.isNullOrBlank()) return ""
    val t = url.trim()
    return when {
        t.startsWith("http://") || t.startsWith("https://") -> t
        t.startsWith("/") -> "https://dragonball-api.com$t"
        else -> "https://dragonball-api.com/$t"
    }
}

fun CharacterDto.toDomain(): Card = Card(
    image = normalizeImage(image),
    value = "N/A"
)
