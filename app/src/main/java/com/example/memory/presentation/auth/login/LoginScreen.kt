package com.example.memory.presentation.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

// --- UI-Only Composable ---
@Composable
fun LoginScreenUI(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    isLoading: Boolean,
    error: String?,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onLoginClick,
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isLoading) "Logging in..." else "Login")
        }

        error?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreenUI(
        email = "",
        password = "",
        isLoading = false,
        error = null,
        onEmailChange = {},
        onPasswordChange = {},
        onLoginClick = {}
    )
}

// --- Wrapper Composable with ViewModel ---
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit
) {
    val state = viewModel.state

    // 🔁 Navigate if login is successful
    if (state.isSuccess) {
        onLoginSuccess()
    }

    LoginScreenUI(
        email = state.email,
        password = state.password,
        isLoading = state.isLoading,
        error = state.error,
        onEmailChange = { viewModel.onEvent(LoginIntent.EmailChanged(it)) },
        onPasswordChange = { viewModel.onEvent(LoginIntent.PasswordChanged(it)) },
        onLoginClick = { viewModel.onEvent(LoginIntent.LoginClicked) }
    )
}
