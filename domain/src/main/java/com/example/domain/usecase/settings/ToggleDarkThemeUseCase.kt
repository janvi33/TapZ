package com.example.domain.usecase.settings

import com.example.domain.repository.SettingsRepository

class ToggleDarkThemeUseCase(val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(){
        return settingsRepository.toggleDarkTheme()
    }
}