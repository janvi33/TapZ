package com.example.domain.repository

import com.example.domain.model.Player

interface PlayerRepository {
    suspend fun getAllPlayers(): List<Player>
    suspend fun getPlayer(player: Player): Player
}