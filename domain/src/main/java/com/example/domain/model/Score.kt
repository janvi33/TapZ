package com.example.domain.model

data class Score(
    val id: Int = 0,
    val playerEmail: String,
    val bestScore: Int,
    val bestTime: Int
)