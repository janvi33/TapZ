package com.example.memory.presentation.settings

data class SettingsState(
    val isDarkTheme: Boolean = false,
    val isSoundOn: Boolean = true,
    val isLoggedOut: Boolean = false
)
