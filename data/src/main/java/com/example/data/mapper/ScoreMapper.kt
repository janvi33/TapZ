package com.example.data.mapper

import com.example.data.local.entity.ScoreEntity
import com.example.domain.model.Score

fun ScoreEntity.toDomain(): Score = Score(
    id = id,
    playerEmail = playerEmail,
    bestScore = bestScore,
    bestTime = bestTime
)

fun Score.toEntity(): ScoreEntity = ScoreEntity(
    id = id,
    playerEmail = playerEmail,
    bestScore = bestScore,
    bestTime = bestTime
)
