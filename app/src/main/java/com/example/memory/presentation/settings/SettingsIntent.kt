package com.example.memory.presentation.settings

sealed class SettingsIntent {
    data object ToggleTheme : SettingsIntent()
    data object ToggleSound : SettingsIntent()
    data object LogoutClicked : SettingsIntent()
}
