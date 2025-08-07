package com.example.memory.presentation.game

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.memory.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    difficulty: String,
    navController: NavController,
    viewModel: GameViewModel = hiltViewModel()
) {
    val state = viewModel.state
    var showExitDialog by remember { mutableStateOf(false) }


    // TopBar with difficulty, timer and score
    Column(modifier = Modifier.fillMaxSize()) {


        TopAppBar(
            title = {
                Text("Difficulty: ${state.difficulty} | Time: ${state.timeElapsed}s | Score: ${state.score}")
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.cards.size) { index ->
                    val card = state.cards[index]
                    CardItem(
                        emoji = card.imageUrl,
                        isFlipped = card.isFlipped || card.isMatched,
                        onClick = {
                            if (!card.isFlipped && !card.isMatched && !state.isGameOver) {
                                viewModel.onEvent(GameIntent.CardClicked(index))
                            }
                        }
                    )
                }
            }



            // Bottom buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { viewModel.onEvent(GameIntent.RestartGame) }) {
                    Text("Restart")
                }

                Button(onClick = { showExitDialog = true }) {
                    Text("Menu")
                }

            }

            if (showExitDialog) {
                AlertDialog(
                    onDismissRequest = { showExitDialog = false },
                    title = { Text("Exit Game?") },
                    text = { Text("Are you sure you want to exit to the menu? Your current game will be lost.") },
                    confirmButton = {
                        TextButton(onClick = {
                            showExitDialog = false
                            viewModel.onEvent(GameIntent.GoToMenu)
                            navController.navigate(Routes.MENU) {
                                popUpTo(Routes.GAME) { inclusive = true }
                            }
                        }) {
                            Text("Exit")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showExitDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }



            var showWinDialog by remember { mutableStateOf(false) }
            var showDifficultyDialog by remember { mutableStateOf(false) }

            if (showWinDialog) {
                AlertDialog(
                    onDismissRequest = { showWinDialog = false },
                    title = { Text("🎉 You Won!") },
                    text = {
                        Text(
                            "Difficulty: ${state.difficulty}\n" +
                                    "Score: ${state.score}\n" +
                                    "Time: ${state.timeElapsed} seconds"
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            showWinDialog = false
                            viewModel.onEvent(GameIntent.RestartGame)
                        }) {
                            Text("Play Again")
                        }
                    },
                    dismissButton = {
                        Column {
                            TextButton(onClick = {
                                showWinDialog = false
                                showDifficultyDialog = true
                            }) {
                                Text("Change Difficulty")
                            }

                            TextButton(onClick = {
                                showWinDialog = false
                                navController.navigate(Routes.MENU) {
                                    popUpTo(Routes.GAME) { inclusive = true }
                                }
                            }) {
                                Text("Exit to Menu")
                            }
                        }
                    }
                )
            }


            if (showDifficultyDialog) {
                AlertDialog(
                    onDismissRequest = { showDifficultyDialog = false },
                    title = { Text("Select Difficulty") },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            listOf("Easy", "Medium", "Hard").forEach { level ->
                                Button(onClick = {
                                    showDifficultyDialog = false
                                    showWinDialog = false
                                    navController.navigate("${Routes.GAME}/$level") {
                                        popUpTo(Routes.GAME) { inclusive = true }
                                    }
                                }) {
                                    Text(level)
                                }
                            }
                        }
                    },
                    confirmButton = {},
                    dismissButton = {}
                )
            }




            LaunchedEffect(state.isGameOver) {
                if (state.isGameOver) {
                    showWinDialog = true
                }
            }




        }
    }
}

@Composable
fun CardItem(
    emoji: String,
    isFlipped: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(120.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isFlipped) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            if (isFlipped) {
                Text(text = emoji, fontSize = 24.sp)
            } else {
                Text(text = "❓", fontSize = 24.sp)
            }
        }
    }
}

