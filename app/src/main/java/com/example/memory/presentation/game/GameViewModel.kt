package com.example.memory.presentation.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.CardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class GameViewModel @Inject constructor(
    private val cardRepository: CardRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val difficulty: String = savedStateHandle["difficulty"] ?: "Easy"

    var state by mutableStateOf(GameState(difficulty = difficulty))
        private set

    private var timerRunning = false

    init {
        startNewGame()
    }

    fun onEvent(intent: GameIntent) {
        when (intent) {
            is GameIntent.CardClicked -> handleCardClick(intent.index)
            is GameIntent.RestartGame -> restartGame()
            is GameIntent.GoToMenu -> { /* handled in UI */ }
            is GameIntent.TimerTick -> incrementTimer()
            is GameIntent.ErrorOccurred -> state = state.copy(error = intent.message)
        }
    }

    private fun incrementTimer() {
        if (!state.isGameOver) {
            state = state.copy(timeElapsed = state.timeElapsed + 1)
        }
    }

    private fun startNewGame() {
        state = state.copy(
            isLoading = true,
            isGameOver = false,
            score = 0,
            timeElapsed = 0,
            matchedIndices = emptySet(),
            flippedCardIndex = null,
            error = null
        )

        viewModelScope.launch {
            try {
                val numberOfPairs = when (difficulty) {
                    "Easy" -> 3
                    "Medium" -> 6
                    "Hard" -> 8
                    else -> 6
                }

                // 1) Fetch domain cards (with image URLs) from API via repository
                val domainCards = cardRepository.getAllCards()

                if (domainCards.isEmpty()) {
                    state = state.copy(isLoading = false, error = "No cards returned from API")
                    return@launch
                }

                // 2) Pick as many images as we need; if fewer available, cycle them
                val baseImages = domainCards.map { it.image.orEmpty() }.filter { it.isNotBlank() }
                if (baseImages.isEmpty()) {
                    state = state.copy(isLoading = false, error = "API returned empty image URLs")
                    return@launch
                }

                val images: List<String> =
                    if (baseImages.size >= numberOfPairs) {
                        baseImages.take(numberOfPairs)
                    } else {
                        generateSequence { baseImages }.flatten().take(numberOfPairs).toList()
                    }

                // 3) Create pairs and shuffle
                val imagePairs = (images + images).shuffled()

                // 4) Map to UI cards
                val cards = imagePairs.mapIndexed { index, url ->
                    GameCard(id = index, imageUrl = url)
                }

                state = state.copy(cards = cards, isLoading = false)
                startTimer()
            } catch (e: Exception) {
                e.printStackTrace()
                state = state.copy(isLoading = false, error = e.message ?: "Failed to load cards")
            }
        }
    }

    private fun handleCardClick(index: Int) {
        if (state.isGameOver || state.cards[index].isMatched || state.cards[index].isFlipped) return

        val cards = state.cards.toMutableList()

        if (state.flippedCardIndex == null) {
            // Flip first card
            cards[index] = cards[index].copy(isFlipped = true)
            state = state.copy(cards = cards, flippedCardIndex = index)
        } else {
            val firstIndex = state.flippedCardIndex ?: return
            cards[index] = cards[index].copy(isFlipped = true)
            state = state.copy(cards = cards)

            viewModelScope.launch {
                val firstCard = state.cards[firstIndex]
                val secondCard = state.cards[index]
                val updatedCards = state.cards.toMutableList()

                delay(700)

                if (firstCard.imageUrl == secondCard.imageUrl) {
                    // Matched
                    updatedCards[firstIndex] = firstCard.copy(isMatched = true)
                    updatedCards[index] = secondCard.copy(isMatched = true)
                    state = state.copy(
                        cards = updatedCards,
                        flippedCardIndex = null,
                        matchedIndices = state.matchedIndices + setOf(firstIndex, index),
                        score = state.score + 10
                    )
                } else {
                    // Not matched — flip back both
                    updatedCards[firstIndex] = firstCard.copy(isFlipped = false)
                    updatedCards[index] = secondCard.copy(isFlipped = false)
                    state = state.copy(
                        cards = updatedCards,
                        flippedCardIndex = null
                    )
                }

                if (updatedCards.all { it.isMatched }) {
                    state = state.copy(isGameOver = true)
                }
            }
        }
    }

    private fun startTimer() {
        if (timerRunning) return
        timerRunning = true
        viewModelScope.launch {
            while (!state.isGameOver) {
                delay(1000)
                onEvent(GameIntent.TimerTick)
            }
            timerRunning = false
        }
    }

    private fun restartGame() {
        timerRunning = false
        startNewGame()
    }
}
