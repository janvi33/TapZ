package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey val id: Int = 0, // You can use a constant ID if you only save one row
    val darkMode: Boolean,
    val sfxToggle: Boolean,
    val musicToggle: Boolean,
    val masterVolumeToggle: Boolean,
    val sfxVolume: Float,
    val musicVolume: Float,
    val masterVolume: Float
)
