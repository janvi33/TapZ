package com.example.domain.usecase

import com.example.domain.model.Score
import com.example.domain.repository.ScoreRepository

class SaveScoreUseCase(val scoreRepository: ScoreRepository) {
    suspend operator fun invoke(score: Score) {
        scoreRepository.saveScore(score)
    }
}