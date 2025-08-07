package com.example.domain.model

data class Player(
    val email: String,
    val name: String,
    val joinedDate: String,
    val streak: Int,
    val totalScore: Int
)
