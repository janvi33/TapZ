package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey


@Entity(
    tableName = "score")

data class ScoreEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val playerEmail: String,
    val bestScore: Int,
    val bestTime: Int
)

