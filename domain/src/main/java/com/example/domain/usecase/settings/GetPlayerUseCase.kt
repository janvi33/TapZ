package com.example.domain.usecase.settings

import com.example.domain.model.Player
import com.example.domain.repository.PlayerRepository

class GetPlayerUseCase(val playerRepository: PlayerRepository) {
    suspend operator fun invoke(player: Player): Player {
        return playerRepository.getPlayer(player)
    }
}