package com.example.domain.repository

import com.example.domain.model.Settings

interface SettingsRepository {
    suspend fun getSettings(): Settings
    suspend fun saveSettings(settings: Settings)
}