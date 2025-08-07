package com.example.domain.usecase.settings

import com.example.domain.model.Settings
import com.example.domain.repository.SettingsRepository

class SaveSettingsUseCase(val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(settings: Settings) {
        settingsRepository.saveSettings(settings)
    }
}