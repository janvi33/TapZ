package com.example.memory.presentation.auth.login

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import android.util.Patterns

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    // State representing UI
    var state by mutableStateOf(LoginState())
        private set

    // Handle user actions (Intents)
    fun onEvent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EmailChanged -> {
                state = state.copy(email = intent.email)
            }

            is LoginIntent.PasswordChanged -> {
                state = state.copy(password = intent.password)
            }

            is LoginIntent.LoginClicked -> {
                loginUser(state.email, state.password)
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        // Avoid duplicate clicks
        if (state.isLoading) return

        // Basic email format check
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            state = state.copy(error = "Invalid email format")
            return
        }

        state = state.copy(isLoading = true, error = null)

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    state = state.copy(isLoading = false, isSuccess = true)
                } else {
                    state = state.copy(
                        isLoading = false,
                        error = task.exception?.localizedMessage ?: "Login failed"
                    )
                }
            }
    }

    // Optional: Reset success manually if needed after navigation
    fun resetSuccessFlag() {
        state = state.copy(isSuccess = false)
    }
}
