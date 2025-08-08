package com.example.data.repository

import com.example.data.local.dao.PlayerDao
import com.example.data.local.entity.PlayerEntity
import com.example.domain.model.Player
import com.example.domain.repository.PlayerRepository

class PlayerRepositoryImpl(
    private val dao: PlayerDao
) : PlayerRepository {

    override suspend fun getAllPlayers(): List<Player> {
        // You can later add this to your DAO
        throw NotImplementedError("getAllPlayers() not yet implemented")
    }

    override suspend fun getPlayer(player: Player): Player {
        val entity = dao.getPlayer(player.email)
        return entity.toDomain()
    }

    private fun Player.toEntity(): PlayerEntity = PlayerEntity(
        id = id,
        name = name,
        email = email
    )

    private fun PlayerEntity.toDomain(): Player = Player(
        id = id,
        name = name,
        email = email
    )
}
