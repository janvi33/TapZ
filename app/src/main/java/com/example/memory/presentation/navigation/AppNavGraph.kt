package com.example.memory.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.memory.presentation.auth.login.LoginScreen
import com.example.memory.presentation.menu.MenuScreen
import com.example.memory.presentation.game.GameScreen
import com.example.memory.presentation.score.ScoresScreen
import com.example.memory.presentation.settings.SettingsScreen

object Routes {
    const val LOGIN = "login"
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
            SettingsScreen()
        }
    }
}
