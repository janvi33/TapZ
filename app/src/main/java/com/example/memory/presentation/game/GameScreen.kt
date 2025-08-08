package com.example.memory.presentation.game

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.memory.R
import com.example.memory.presentation.navigation.BottomNavItem
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
    var showWinDialog by remember { mutableStateOf(false) }
    var showDifficultyDialog by remember { mutableStateOf(false) }

    val columns = when (state.difficulty) {
        "Easy" -> 3
        "Medium" -> 4
        "Hard" -> 4
        else -> 4
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        TopBarFancy(
            title = "TAP-Z",
            difficulty = state.difficulty,
            time = "${state.timeElapsed}s",
            score = state.score
        )

        if (state.error != null) {
            Spacer(Modifier.height(8.dp))
            AssistChip(
                onClick = {},
                label = { Text(state.error ?: "Error") },
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }

        Spacer(Modifier.height(8.dp))

        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(10.dp)
            ) {
                items(state.cards, key = { it.id }) { card ->
                    CardItem(
                        imageUrl = card.imageUrl,
                        isFlipped = card.isFlipped || card.isMatched,
                        isMatched = card.isMatched,
                        onClick = {
                            if (!card.isFlipped && !card.isMatched && !state.isGameOver) {
                                viewModel.onEvent(GameIntent.CardClicked(card.id))
                            }
                        }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(onClick = { viewModel.onEvent(GameIntent.RestartGame) }) {
                    Text("Restart")
                }
                Button(onClick = { showExitDialog = true }) {
                    Text("Menu")
                }
            }
        }
    }

    if (showExitDialog) {
        PrettyDialog(
            title = "Exit Game?",
            text = "Exit to the menu? Your current game will be lost.",
            confirmText = "Exit",
            dismissText = "Cancel",
            onConfirm = {
                showExitDialog = false
                viewModel.onEvent(GameIntent.GoToMenu)
                navController.popBackStack(BottomNavItem.Start.route, inclusive = false)
            },
            onDismiss = { showExitDialog = false }
        )
    }

    if (showWinDialog) {
        WinDialog(
            difficulty = state.difficulty,
            score = state.score,
            time = state.timeElapsed,
            onPlayAgain = {
                showWinDialog = false
                viewModel.onEvent(GameIntent.RestartGame)
            },
            onChangeDifficulty = {
                showWinDialog = false
                showDifficultyDialog = true
            },
            onExitToMenu = {
                showWinDialog = false
                viewModel.onEvent(GameIntent.GoToMenu)
                navController.popBackStack(BottomNavItem.Start.route, inclusive = false)
            }
        )
    }

    if (showDifficultyDialog) {
        DifficultyDialog(
            options = listOf("Easy", "Medium", "Hard"),
            onPick = { level ->
                showDifficultyDialog = false
                navController.popBackStack("${BottomNavItem.Start.route}/${state.difficulty}", inclusive = true)
                navController.navigate("${BottomNavItem.Start.route}/$level")
            },

            onDismiss = { showDifficultyDialog = false }
        )
    }

    LaunchedEffect(state.isGameOver) {
        if (state.isGameOver) showWinDialog = true
    }
}

/* -------------------- Fancy Top Bar -------------------- */
@Composable
private fun TopBarFancy(
    title: String,
    difficulty: String,
    time: String,
    score: Int
) {
    val bg = Brush.horizontalGradient(
        listOf(Color(0xFFFFF3E0), Color(0xFFFFE0B2)) // very light orange sweep
    )
    val accent = Color(0xFFFFA726) // orange accent (not neon)

    // Only an orange glow line as the “shadow”
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp)
    ) {
        Surface(
            color = Color.Transparent,
            shape = RoundedCornerShape(22.dp),
            tonalElevation = 0.dp,
            shadowElevation = 0.dp,
            modifier = Modifier
                .fillMaxWidth()
                .background(bg, RoundedCornerShape(22.dp))
                .border(
                    1.dp,
                    Brush.linearGradient(
                        listOf(Color(0x22FFB74D), Color(0x11FF8A65))
                    ),
                    RoundedCornerShape(22.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Stronger title, warm dark text
                Text(
                    text = title, // "TAP-Z"
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3E2723)
                    )
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.horizontalScroll(rememberScrollState())
                ) {
                    Pill("🎯", difficulty)
                    Pill("⏱", time)
                    Pill("⭐", score.toString())
                }
            }
        }
    }
}




@Composable
private fun Pill(icon: String, text: String) {
    val outline = Brush.linearGradient(
        listOf(Color(0x14FFAB91), Color(0x10FFCC80))
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(MaterialTheme.colorScheme.surface) // lighter than bar
            .border(1.dp, outline, RoundedCornerShape(999.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp)
            .heightIn(min = 28.dp)
    ) {
        Text(icon)
        Spacer(Modifier.width(6.dp))
        Text(
            text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

/* -------------------- Win Dialog (prettier + aligned) -------------------- */
@Composable
private fun WinDialog(
    difficulty: String,
    score: Int,
    time: Int,
    onPlayAgain: () -> Unit,
    onChangeDifficulty: () -> Unit,
    onExitToMenu: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onChangeDifficulty,
        confirmButton = {},
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .border(
                        1.dp,
                        Brush.linearGradient(
                            listOf(Color(0x22FFFFFF), Color(0x11FFFFFF))
                        ),
                        RoundedCornerShape(24.dp)
                    )
            ) {
                // Gradient header with confetti
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xFFFFD180), Color(0xFFFFAB91))
                            )
                        )
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🎊🎉✨", style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = "You Won!",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF5D4037)
                            )
                        )
                    }
                }

                // Stats row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    WinStat(label = "Difficulty", value = difficulty, modifier = Modifier.weight(1f))
                    WinStat(label = "Score", value = score.toString(), modifier = Modifier.weight(1f))
                    WinStat(label = "Time", value = "${time}s", modifier = Modifier.weight(1f))
                }

                // Actions (equal width, single row)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = onPlayAgain,
                        modifier = Modifier.weight(1f)
                    ) { Text("Play Again") }

                    OutlinedButton(
                        onClick = onChangeDifficulty,
                        modifier = Modifier.weight(1f)
                    ) { Text("Difficulty") }
                }

                TextButton(
                    onClick = onExitToMenu,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 6.dp, top = 2.dp)
                ) { Text("Exit to Menu") }
            }
        },
        shape = RoundedCornerShape(28.dp),
        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        tonalElevation = 10.dp
    )
}

@Composable
private fun WinStat(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(4.dp))
        Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
    }
}

/* -------------------- Generic Dialogs -------------------- */
@Composable
private fun PrettyDialog(
    title: String,
    text: String,
    confirmText: String,
    dismissText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onConfirm) { Text(confirmText) }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) { Text(dismissText) }
        },
        title = { Text(title, style = MaterialTheme.typography.titleLarge) },
        text = { Text(text) },
        shape = RoundedCornerShape(24.dp),
        tonalElevation = 6.dp,
        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
    )
}

/* -------------------- Difficulty Dialog (balanced) -------------------- */
@Composable
private fun DifficultyDialog(
    options: List<String>,
    onPick: (String) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        title = { Text("Select Difficulty", style = MaterialTheme.typography.titleLarge) },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                options.forEach { level ->
                    Button(
                        onClick = { onPick(level) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) { Text(level, maxLines = 1, softWrap = false) }
                }
            }
        },
        dismissButton = { OutlinedButton(onClick = onDismiss) { Text("Close") } },
        shape = RoundedCornerShape(24.dp),
        tonalElevation = 6.dp,
        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
    )
}



/* -------------------- Card Item (effects + light orange theme) -------------------- */
@Composable
fun CardItem(
    imageUrl: String,
    isFlipped: Boolean,
    isMatched: Boolean,
    onClick: () -> Unit,
    backImageRes: Int = R.drawable.cardback
) {
    val cardFront = Color(0xFFFFE0B2) // light orange 200
    val cardBack = Color(0xFFFFF3E0)  // lighter orange 50

    val scale by animateFloatAsState(
        targetValue = if (isFlipped) 1.0f else 0.98f,
        label = "scale"
    )

    val borderBrush = if (isMatched)
        Brush.linearGradient(listOf(Color(0xFFFFB74D), Color(0xFFFF8A65)))
    else if (isFlipped)
        Brush.linearGradient(listOf(Color(0xFFFFCC80), Color(0xFFFFAB91)))
    else
        Brush.linearGradient(listOf(Color(0xFFFFE0B2), Color(0xFFFFF3E0)))

    val glowColor = if (isMatched) Color(0xFFFFCC80) else Color.Transparent

    Card(
        modifier = Modifier
            .aspectRatio(3f / 4f)
            .shadow(if (isMatched) 10.dp else 6.dp, RoundedCornerShape(18.dp))
            .clip(RoundedCornerShape(18.dp))
            .border(width = 2.dp, brush = borderBrush, shape = RoundedCornerShape(18.dp))
            .drawBehind {
                if (glowColor != Color.Transparent) {
                    withTransform({
                        translate(-size.width * 0.05f, -size.height * 0.05f)
                    }) {
                        drawRect(
                            color = glowColor.copy(alpha = 0.25f),
                            size = size * 1.1f
                        )
                    }
                }
            }
            .clickable { onClick() }
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
        elevation = CardDefaults.cardElevation(defaultElevation = if (isMatched) 8.dp else 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isFlipped) cardFront else cardBack
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isFlipped) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Card image",
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.TopCenter,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Image(
                    painter = painterResource(id = backImageRes),
                    contentDescription = "Card back",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxSize()
                )
            }
        }
    }
}
