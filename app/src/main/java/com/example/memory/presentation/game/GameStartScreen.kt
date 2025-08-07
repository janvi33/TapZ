package com.example.memory.presentation.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.memory.R
import com.example.memory.presentation.navigation.BottomNavItem
import com.example.memory.presentation.ui.components.AnimatedButton
import com.example.memory.presentation.ui.components.StrokedText

@Composable
fun GameStartScreen(navController: NavController) {
    val difficultyLevels = listOf("Easy", "Medium", "Hard")
    var selectedDifficulty by remember { mutableStateOf<String?>(null) }

    val backgroundPainter = painterResource(id = R.drawable.gamebackground)

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = backgroundPainter,
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .align(Alignment.Center),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.6f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StrokedText(
                    text = "🧠 Select Difficulty",
                    style = MaterialTheme.typography.headlineMedium,
                    strokeColor = Color.Black,
                    strokeWidth = 1.dp,
                    fillColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 24.dp)
                )


                // Vertical list of difficulty buttons with spacing
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    difficultyLevels.forEach { difficulty ->
                        val isSelected = difficulty == selectedDifficulty
                        AnimatedButton(
                            onClick = { selectedDifficulty = difficulty },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        ) {
                            Text(
                                text = difficulty,
                                style = MaterialTheme.typography.titleLarge,
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                AnimatedButton(
                    onClick = {
                        selectedDifficulty?.let {
                            navController.navigate("${BottomNavItem.Start.route}/$it")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = selectedDifficulty != null,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Text(
                        "🎮 Start Game",
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }
        }
    }
}
