package com.example.data.mapper

import com.example.data.local.entity.ScoreEntity
import com.example.domain.model.Score

fun ScoreEntity.toDomain(): Score {
    return Score(
        bestScore = bestScore,
        bestTime = bestTime
    )
}

fun Score.toEntity(): ScoreEntity {
    return ScoreEntity(
        bestScore = bestScore,
        bestTime = bestTime
    )
}
