package com.example.memory.presentation.settings

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.*

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    var state by mutableStateOf(SettingsState())
        private set

    fun onEvent(event: SettingsIntent) {
        when (event) {
            SettingsIntent.ToggleTheme -> {
                state = state.copy(isDarkTheme = !state.isDarkTheme)
                // You can persist this with DataStore if needed
            }

            SettingsIntent.ToggleSound -> {
                state = state.copy(isSoundOn = !state.isSoundOn)
                // Also persist this if needed
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
