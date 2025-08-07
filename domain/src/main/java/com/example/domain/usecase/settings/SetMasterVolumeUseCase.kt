package com.example.domain.usecase.settings

import com.example.domain.repository.SettingsRepository

class SetMasterVolumeUseCase(val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(volume: Float) {
        settingsRepository.setMasterVolume(volume)
    }
}