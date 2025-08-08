package com.example.data.repository

import com.example.data.local.dao.SettingsDao
import com.example.data.mapper.toDomain
import com.example.data.mapper.toEntity
import com.example.domain.model.Settings
import com.example.domain.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SettingsRepositoryImpl(
    private val dao: SettingsDao
) : SettingsRepository {

    override suspend fun getSettings(): Settings = withContext(Dispatchers.IO) {
        dao.getSettings()?.toDomain() ?: defaultSettings()
    }

    override suspend fun saveSettings(settings: Settings) = withContext(Dispatchers.IO) {
        dao.insertSettings(settings.toEntity())
    }

    override suspend fun toggleDarkTheme() = withContext(Dispatchers.IO) {
        val current = getSettings()
        val updated = current.copy(darkMode = !current.darkMode)
        saveSettings(updated)
    }

    override suspend fun toggleSoundEffects() = withContext(Dispatchers.IO) {
        val current = getSettings()
        val updated = current.copy(sfxToggle = !current.sfxToggle)
        saveSettings(updated)
    }

    override suspend fun toggleMusic() = withContext(Dispatchers.IO) {
        val current = getSettings()
        val updated = current.copy(musicToggle = !current.musicToggle)
        saveSettings(updated)
    }

    override suspend fun toggleMasterVolume() = withContext(Dispatchers.IO) {
        val current = getSettings()
        val updated = current.copy(masterVolumeToggle = !current.masterVolumeToggle)
        saveSettings(updated)
    }

    override suspend fun setSfxVolume(volume: Float) = withContext(Dispatchers.IO) {
        val current = getSettings()
        val updated = current.copy(sfxVolume = volume)
        saveSettings(updated)
    }

    override suspend fun setMusicVolume(volume: Float) = withContext(Dispatchers.IO) {
        val current = getSettings()
        val updated = current.copy(musicVolume = volume)
        saveSettings(updated)
    }

    override suspend fun setMasterVolume(volume: Float) = withContext(Dispatchers.IO) {
        val current = getSettings()
        val updated = current.copy(masterVolume = volume)
        saveSettings(updated)
    }

    private fun defaultSettings(): Settings {
        return Settings(
            darkMode = false,
            sfxToggle = true,
            musicToggle = true,
            masterVolumeToggle = true,
            sfxVolume = 0.5f,
            musicVolume = 0.5f,
            masterVolume = 0.5f
        )
    }
}
