package com.example.domain.repository

import com.example.domain.model.Settings

interface SettingsRepository {
    suspend fun getSettings(): Settings
    suspend fun saveSettings(settings: Settings)

    suspend fun toggleDarkTheme()

    suspend fun toggleSoundEffects()

    suspend fun toggleMusic()

    suspend fun toggleMasterVolume()

    suspend fun setSfxVolume(volume: Float)

    suspend fun setMusicVolume(volume: Float)

    suspend fun setMasterVolume(volume: Float)

}