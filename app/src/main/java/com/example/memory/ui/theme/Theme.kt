package com.example.memory.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val AccentGold = Color(0xF0FFD882) // darker gold accent color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFFF812C),      // Super Saiyan Gold
    secondary = Color(0xFF2196F3),
    tertiary = AccentGold,      // Kamehameha Blue
    background = Color(0xFFFFF8E1),   // Light Aura Background
    surface = Color(0xFFFFFFFF),      // Capsule White
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    error = Color(0xFFD32F2F)         // Frieza Red
)


private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFF9800),      // Saiyan Orange
    secondary = Color(0xFF03A9F4),    // Power Blue
    tertiary = AccentGold,
    background = Color(0xFF121212),   // Deep Space
    surface = Color(0xFF2C2C2C),      // Capsule Gray
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    error = Color(0xFFF44336)         // DBZ Danger Red
)



    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */


@Composable
fun MemoryTheme(
    darkTheme: Boolean, // no default
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}