package com.example.memory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.memory.presentation.auth.login.LoginScreen
import com.example.memory.presentation.navigation.AppNavGraph
import com.example.memory.presentation.settings.SettingsIntent
import com.example.memory.presentation.settings.SettingsViewModel
import com.example.memory.ui.theme.MemoryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: SettingsViewModel = hiltViewModel()
            val state = viewModel.state


            MemoryTheme(darkTheme = state.isDarkTheme) {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        settingsState = state,
                        onToggleTheme = { viewModel.onEvent(SettingsIntent.ToggleTheme) },
                        onToggleSound = { viewModel.onEvent(SettingsIntent.ToggleSound) },
                        onLogoutConfirmed = { viewModel.onEvent(SettingsIntent.LogoutClicked) },
                        onResetLogout = { viewModel.resetLogoutFlag() }
                    )

                }
            }
        }

    }
}