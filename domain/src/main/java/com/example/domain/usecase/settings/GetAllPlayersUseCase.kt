package com.example.domain.usecase.settings

import com.example.domain.model.Player
import com.example.domain.repository.PlayerRepository

class GetAllPlayersUseCase(val playerRepository: PlayerRepository) {
    suspend operator fun invoke(): List<Player> {
        return playerRepository.getAllPlayers()
    }
}