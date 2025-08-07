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
        dao.getSettings()?.toDomain() ?: Settings(
            darkMode = false,
            sfxToggle = true,
            musicToggle = true,
            masterVolumeToggle = true,
            sfxVolume = 0.5f,
            musicVolume = 0.5f,
            masterVolume = 0.5f
        )
    }

    override suspend fun saveSettings(settings: Settings) = withContext(Dispatchers.IO) {
        dao.insertSettings(settings.toEntity())
    }
}
