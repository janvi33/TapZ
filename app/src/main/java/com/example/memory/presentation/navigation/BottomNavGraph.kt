//package com.example.memory.presentation.navigation
//
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import com.example.memory.presentation.game.GameStartScreen
//import com.example.memory.presentation.score.ScoresScreen
//import com.example.memory.presentation.settings.SettingsScreen
//import com.example.memory.presentation.settings.SettingsState
//
//@Composable
//fun BottomNavGraph(
//    navController: NavHostController,
//    modifier: Modifier = Modifier,
//    onStartGame: (String) -> Unit,
//    settingsState: SettingsState,
//    onToggleTheme: () -> Unit,
//    onToggleSound: () -> Unit,
//    onLogoutConfirmed: () -> Unit,
//    onEditProfile: () -> Unit
//) {
//    NavHost(
//        navController = navController,
//        startDestination = Routes.TAB_START,
//        modifier = modifier
//    ) {
//        composable(Routes.TAB_START) {
//            GameStartScreen(onStartGame = onStartGame)
//        }
//        composable(Routes.TAB_SCORES) {
//            ScoresScreen()
//        }
//        composable(Routes.TAB_SETTINGS) {
//            SettingsScreen(
//                isDarkTheme = settingsState.isDarkTheme,
//                isSoundEnabled = settingsState.isSoundOn,
//                onToggleTheme = onToggleTheme,
//                onToggleSound = onToggleSound,
//                onLogoutConfirmed = onLogoutConfirmed,
//                onEditProfile = onEditProfile
//            )
//        }
//    }
//}
