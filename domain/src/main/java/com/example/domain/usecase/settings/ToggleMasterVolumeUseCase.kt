package com.example.domain.usecase.settings

import com.example.domain.repository.SettingsRepository

class ToggleMasterVolumeUseCase(val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(){
        return settingsRepository.toggleMasterVolume()
    }
}