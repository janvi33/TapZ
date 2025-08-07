package com.example.domain.repository

import com.example.domain.model.Player

interface PlayerRepository {
    suspend fun insertPlayer(player: Player)
    suspend fun getPlayer(email: String): Player?
}
