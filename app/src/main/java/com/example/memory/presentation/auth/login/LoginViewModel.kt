package com.example.memory.presentation.auth.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.memory.presentation.auth.google.GoogleAuth
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : ViewModel() {

    private val TAG: String = "FirebaseAuth"

    var state by mutableStateOf(LoginState())
        private set

    var googleAuth: GoogleAuth? = null

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
            is LoginIntent.GoogleSignInClicked -> {
                signInWithGoogle()
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        if (state.isLoading) return
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

    private fun signInWithGoogle() {
        val auth = googleAuth ?: return

        state = state.copy(isLoading = true)

        viewModelScope.launch {
            auth.signInWithGoogle { success, user, error ->
                if (success && user != null) {
                    Log.d(TAG, "Google Sign-In success: ${user.email}")
                    state = state.copy(isLoading = false, isSuccess = true)
                } else {
                    Log.e(TAG, "Google Sign-In failed: $error")
                    state = state.copy(isLoading = false, error = error ?: "Unknown error")
                }
            }
        }
    }

    fun resetSuccessFlag() {
        state = state.copy(isSuccess = false)
    }
}
