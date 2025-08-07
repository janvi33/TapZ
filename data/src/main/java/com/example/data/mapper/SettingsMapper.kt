package com.example.data.mapper

import com.example.data.local.entity.SettingsEntity
import com.example.domain.model.Settings

fun SettingsEntity.toDomain(): Settings {
    return Settings(
        darkMode = darkMode,
        sfxToggle = sfxToggle,
        musicToggle = musicToggle,
        masterVolumeToggle = masterVolumeToggle,
        sfxVolume = sfxVolume,
        musicVolume = musicVolume,
        masterVolume = masterVolume
    )
}

fun Settings.toEntity(): SettingsEntity {
    return SettingsEntity(
        darkMode = darkMode,
        sfxToggle = sfxToggle,
        musicToggle = musicToggle,
        masterVolumeToggle = masterVolumeToggle,
        sfxVolume = sfxVolume,
        musicVolume = musicVolume,
        masterVolume = masterVolume
    )
}
