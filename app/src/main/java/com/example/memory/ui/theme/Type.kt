package com.example.memory.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.memory.R

// Define the font family using your font resource
val DBZFontFamily = FontFamily(
    Font(R.font.bangers_regular, FontWeight.Normal)
)

// Create a Typography instance using the DBZ font family
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = DBZFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = DBZFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = DBZFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(                 // Add this
        fontFamily = DBZFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp                      // or whatever size you want
    )

    // Add or override more styles if needed
)
