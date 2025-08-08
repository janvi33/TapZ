package com.example.domain.repository

import com.example.domain.model.Score

interface ScoreRepository {
    suspend fun getScore(): List<Score>
    suspend fun saveScore(score: Score)
    suspend fun deleteScore(score: Score)

    suspend fun getScoreForUser(email: String): List<Score> // <-- Add this line


}
