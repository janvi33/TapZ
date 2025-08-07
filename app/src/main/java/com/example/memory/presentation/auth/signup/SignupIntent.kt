package com.example.memory.presentation.auth.signup


sealed class SignupIntent {
    data class EmailChanged(val email: String) : SignupIntent()
    data class PasswordChanged(val password: String) : SignupIntent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : SignupIntent()
    data object SignupClicked : SignupIntent()
}
