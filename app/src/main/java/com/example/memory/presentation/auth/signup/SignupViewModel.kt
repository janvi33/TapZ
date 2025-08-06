package com.example.memory.presentation.auth.signup

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import android.util.Patterns

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    var state by mutableStateOf(SignupState())
        private set

    fun onEvent(intent: SignupIntent) {
        when (intent) {
            is SignupIntent.EmailChanged -> {
                state = state.copy(email = intent.email)
            }
            is SignupIntent.PasswordChanged -> {
                state = state.copy(password = intent.password)
            }
            is SignupIntent.ConfirmPasswordChanged -> {
                state = state.copy(confirmPassword = intent.confirmPassword)
            }
            SignupIntent.SignupClicked -> {
                registerUser(state.email, state.password, state.confirmPassword)
            }
        }
    }

    private fun registerUser(email: String, password: String, confirmPassword: String) {
        if (state.isLoading) return

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            state = state.copy(error = "Invalid email format")
            return
        }

        if (password.length < 6) {
            state = state.copy(error = "Password must be at least 6 characters")
            return
        }

        if (password != confirmPassword) {
            state = state.copy(error = "Passwords do not match")
            return
        }

        state = state.copy(isLoading = true, error = null)

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    state = state.copy(isLoading = false, isSuccess = true)
                } else {
                    state = state.copy(
                        isLoading = false,
                        error = task.exception?.localizedMessage ?: "Signup failed"
                    )
                }
            }
    }

    fun resetSuccessFlag() {
        state = state.copy(isSuccess = false)
    }
}
