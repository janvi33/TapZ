package com.example.memory.presentation.settings

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.SettingsRepository
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val settingsRepository: SettingsRepository

) : ViewModel() {

    var state by mutableStateOf(SettingsState())
        private set

    fun onEvent(event: SettingsIntent) {
        when (event) {
            SettingsIntent.ToggleTheme -> {
                viewModelScope.launch {
                    settingsRepository.toggleDarkTheme()
                    state = state.copy(isDarkTheme = !state.isDarkTheme)
                }
            }
            SettingsIntent.ToggleSound -> {
                viewModelScope.launch {
                    settingsRepository.toggleSoundEffects()
                    state = state.copy(isSoundOn = !state.isSoundOn)
                }
            }
            SettingsIntent.LogoutClicked -> {
                auth.signOut()
                state = state.copy(isLoggedOut = true)
            }
        }
    }


    fun resetLogoutFlag() {
        state = state.copy(isLoggedOut = false)
    }

}
