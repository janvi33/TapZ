package com.example.domain.usecase

import com.example.domain.model.Score
import com.example.domain.repository.ScoreRepository

class GetScoreUseCase(val scoreRepository: ScoreRepository) {
    suspend operator fun invoke(): List<Score> {
        return scoreRepository.getScore()
    }
}