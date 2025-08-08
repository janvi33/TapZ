package com.example.data.repository

import com.example.data.local.dao.PlayerDao
import com.example.data.local.entity.PlayerEntity
import com.example.domain.model.Player
import com.example.domain.repository.PlayerRepository
//
//class PlayerRepositoryImpl(
//    private val dao: PlayerDao
//) : PlayerRepository {
//
//    override suspend fun insertPlayer(player: Player) {
//        dao.insertPlayer(player.toEntity())
//    }
//
//    override suspend fun getPlayer(email: String): Player? {
//        return dao.getPlayer(email)?.toDomain()
//    }
//
//    // Mapping functions
//    private fun Player.toEntity(): PlayerEntity = PlayerEntity(
//        email = email,
//        name = name,
//        joinedDate = joinedDate,
//        streak = streak,
//        totalScore = totalScore
//    )
//
//    private fun PlayerEntity.toDomain(): Player = Player(
//        email = email,
//        name = name,
//        joinedDate = joinedDate,
//        streak = streak,
//        totalScore = totalScore
//    )
//}
