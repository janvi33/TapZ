package com.example.memory.presentation.navigation


import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.memory.R

sealed class BottomNavItem(
    val route: String,
    @DrawableRes val iconRes: Int,
    val label: String
) {
    object Start : BottomNavItem("start", R.drawable.startgame, "Start")
    object Score : BottomNavItem("score", R.drawable.score, "Score")
    object Settings : BottomNavItem("settings", R.drawable.settings, "Settings")
}

