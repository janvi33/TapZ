package com.example.memory.presentation.game

sealed class GameIntent {
    data class CardClicked(val index: Int) : GameIntent()
    data object RestartGame : GameIntent()
    data object GoToMenu : GameIntent()
    data object TimerTick : GameIntent()
    data class ErrorOccurred(val message: String) : GameIntent()
}
