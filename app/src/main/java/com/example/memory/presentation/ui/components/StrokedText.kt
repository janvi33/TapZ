package com.example.memory.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun StrokedText(
    text: String,
    style: TextStyle,
    strokeColor: Color = Color.Black,
    strokeWidth: Dp = 1.dp,
    fillColor: Color = Color.White,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        // Draw "stroke" by drawing text offset in 4 directions
        Text(
            text = text,
            style = style,
            color = strokeColor,
            modifier = Modifier
                .offset(x = -strokeWidth, y = -strokeWidth)
        )
        Text(
            text = text,
            style = style,
            color = strokeColor,
            modifier = Modifier
                .offset(x = strokeWidth, y = -strokeWidth)
        )
        Text(
            text = text,
            style = style,
            color = strokeColor,
            modifier = Modifier
                .offset(x = -strokeWidth, y = strokeWidth)
        )
        Text(
            text = text,
            style = style,
            color = strokeColor,
            modifier = Modifier
                .offset(x = strokeWidth, y = strokeWidth)
        )
        // Draw main text on top
        Text(
            text = text,
            style = style,
            color = fillColor,
        )
    }
}
