package com.example.memory.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.memory.presentation.game.GameScreen
import com.example.memory.presentation.game.GameStartScreen
import com.example.memory.presentation.score.ScoresScreen
import com.example.memory.presentation.settings.SettingsScreen

@Composable
fun AppBottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Start.route
    ) {
        composable(BottomNavItem.Start.route) {
            GameStartScreen(navController)
        }
        composable("${BottomNavItem.Start.route}/{difficulty}") { backStackEntry ->
            val difficulty = backStackEntry.arguments?.getString("difficulty") ?: "Easy"
            GameScreen(difficulty = difficulty, navController = navController)
        }
        composable(BottomNavItem.Score.route) {
            ScoresScreen()
        }
        composable(BottomNavItem.Settings.route) {
            SettingsScreen(
                onLogoutConfirmed = {
                    // After Firebase signOut (done in VM), go to Start tab
                    navController.navigate(BottomNavItem.Start.route) {
                        popUpTo(BottomNavItem.Start.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
