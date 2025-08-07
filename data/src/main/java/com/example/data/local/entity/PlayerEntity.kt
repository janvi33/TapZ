package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player")
data class PlayerEntity(
    @PrimaryKey val email: String,
    val name: String,
    val joinedDate: String,
    val streak: Int,
    val totalScore: Int
)
