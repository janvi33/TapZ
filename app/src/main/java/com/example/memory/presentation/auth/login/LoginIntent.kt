package com.example.memory.presentation.auth.login

sealed class LoginIntent {
    data class EmailChanged(val email: String) : LoginIntent()
    data class PasswordChanged(val password: String) : LoginIntent()
    data object LoginClicked : LoginIntent()
    data object GoogleSignInClicked : LoginIntent()
}
