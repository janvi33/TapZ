package com.example.memory.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.memory.presentation.auth.login.LoginScreen
import com.example.memory.presentation.auth.signup.SignupScreen
import com.example.memory.presentation.menu.MenuScreen
import com.example.memory.presentation.game.GameScreen
import com.example.memory.presentation.score.ScoresScreen
import com.example.memory.presentation.settings.SettingsIntent
import com.example.memory.presentation.settings.SettingsScreen
import com.example.memory.presentation.settings.SettingsViewModel

object Routes {
    const val LOGIN = "login"
    const val SIGNUP = "signup"
    const val MENU = "menu"
    const val GAME = "game"
    const val SCORES = "scores"
    const val SETTINGS = "settings"
}

@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = Routes.LOGIN) {

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.MENU) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToSignup = {
                    navController.navigate(Routes.SIGNUP)
                }
            )
        }


        composable(Routes.SIGNUP) {
            SignupScreen(
                onSignupSuccess = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.SIGNUP) { inclusive = true }
                    }
                }
            )
        }


        composable(Routes.MENU) {
            MenuScreen(
                onStartGame = { navController.navigate(Routes.GAME) },
                onViewScores = { navController.navigate(Routes.SCORES) },
                onOpenSettings = { navController.navigate(Routes.SETTINGS) }
            )
        }

        composable(Routes.GAME) {
            GameScreen()
        }

        composable(Routes.SCORES) {
            ScoresScreen()
        }

        composable(Routes.SETTINGS) {
            val viewModel: SettingsViewModel = hiltViewModel()
            val state = viewModel.state

            if (state.isLoggedOut) {
                LaunchedEffect(Unit) {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.MENU) { inclusive = true }
                    }
                    viewModel.resetLogoutFlag()
                }
            }

            SettingsScreen(
                isDarkTheme = state.isDarkTheme,
                isSoundEnabled = state.isSoundOn,
                onToggleTheme = { viewModel.onEvent(SettingsIntent.ToggleTheme) },
                onToggleSound = { viewModel.onEvent(SettingsIntent.ToggleSound) },
                onLogoutConfirmed = { viewModel.onEvent(SettingsIntent.LogoutClicked) }
            )
        }

    }
}
