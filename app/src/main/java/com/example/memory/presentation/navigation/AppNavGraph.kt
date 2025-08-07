package com.example.memory.presentation.navigation

import SplashScreen
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
import com.example.memory.presentation.profile.EditProfileScreen
import com.example.memory.presentation.score.ScoresScreen
import com.example.memory.presentation.settings.SettingsIntent
import com.example.memory.presentation.settings.SettingsScreen
import com.example.memory.presentation.settings.SettingsState
import com.example.memory.presentation.settings.SettingsViewModel

object Routes {
    const val EDIT_PROFILE = "edit_profile"
    const val LOGIN = "login"
    const val SIGNUP = "signup"
    const val MENU = "menu"
    const val GAME = "game"
    const val GAME_WITH_DIFF = "game/{difficulty}"
    const val SCORES = "scores"
    const val SETTINGS = "settings"
    const val SPLASH = "splash"
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    settingsState: SettingsState,
    onToggleTheme: () -> Unit,
    onToggleSound: () -> Unit,
    onLogoutConfirmed: () -> Unit,
    onResetLogout: () -> Unit
){
    NavHost(navController = navController, startDestination = Routes.SPLASH) {

        composable(Routes.SPLASH) {
            SplashScreen(navController)
        }

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
                },
                onNavigateToLogin = {
                    navController.navigate(Routes.LOGIN)
                }
            )
        }


        composable(Routes.MENU) {
            MenuScreen(
                onStartGameWithDifficulty = { difficulty ->
                    navController.navigate("game/$difficulty")
                },
                onViewScores = { navController.navigate(Routes.SCORES) },
                onOpenSettings = { navController.navigate(Routes.SETTINGS) }
            )
        }


//        composable(Routes.GAME) {
//            GameScreen(navController = navController)
//        }

        composable("${Routes.GAME}/{difficulty}") { backStackEntry ->
            val difficulty = backStackEntry.arguments?.getString("difficulty") ?: "Easy"
            GameScreen(difficulty = difficulty, navController = navController)
        }

        composable(Routes.EDIT_PROFILE) {
            EditProfileScreen(navController = navController)
        }





        composable(Routes.SCORES) {
            ScoresScreen(navController = navController)
        }

        composable(Routes.SETTINGS) {
            if (settingsState.isLoggedOut) {
                LaunchedEffect(Unit) {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.MENU) { inclusive = true }
                    }
                    onResetLogout()
                }
            }

            SettingsScreen(
                isDarkTheme = settingsState.isDarkTheme,
                onToggleTheme = onToggleTheme,
                isSoundEnabled = settingsState.isSoundOn,
                onToggleSound = onToggleSound,
                onLogoutConfirmed = onLogoutConfirmed,
                onEditProfile = {
                    navController.navigate(Routes.EDIT_PROFILE)
                },
                navController = navController
            )
        }


    }
}
