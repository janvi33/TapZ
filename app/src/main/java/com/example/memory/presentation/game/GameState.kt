package com.example.memory.presentation.game


data class GameState(
    val difficulty: String = "Easy",
    val timeElapsed: Int = 0, // in seconds
    val score: Int = 0,
    val isGameOver: Boolean = false,
    val isLoading: Boolean = true,
    val cards: List<GameCard> = emptyList(), // image cards to display
    val flippedCardIndex: Int? = null,
    val matchedIndices: Set<Int> = emptySet(),
    val error: String? = null
)

data class GameCard(
    val id: Int,
    val imageUrl: String,
    val isFlipped: Boolean = false,
    val isMatched: Boolean = false
)
