package com.example.domain.usecase.settings

import com.example.domain.repository.SettingsRepository

class SetMusicVolumeUseCase(val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(volume: Float) {
        settingsRepository.setMusicVolume(volume)
    }
}