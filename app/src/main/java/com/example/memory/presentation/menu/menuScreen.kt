//package com.example.memory.presentation.menu
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//
//@Composable
//fun MenuScreen(
//    onStartGameWithDifficulty: (String) -> Unit,
//    onViewScores: () -> Unit,
//    onOpenSettings: () -> Unit
//) {
//    var showDifficultyDialog by remember { mutableStateOf(false) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(32.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "🧠 Memory Match",
//            style = MaterialTheme.typography.headlineLarge,
//            modifier = Modifier.padding(bottom = 48.dp)
//        )
//
//        Button(
//            onClick = { showDifficultyDialog = true },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 12.dp)
//        ) {
//            Text(text = "🎮 Start Game")
//        }
//
//        Button(
//            onClick = onViewScores,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 12.dp)
//        ) {
//            Text(text = "🏆 View Scores")
//        }
//
//        Button(
//            onClick = onOpenSettings,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 12.dp)
//        ) {
//            Text(text = "⚙️ Settings")
//        }
//    }
//
//    if (showDifficultyDialog) {
//        AlertDialog(
//            onDismissRequest = { showDifficultyDialog = false },
//            title = { Text("Choose Difficulty") },
//            text = {
//                Column(
//                    verticalArrangement = Arrangement.spacedBy(12.dp)
//                ) {
//                    Button(onClick = {
//                        showDifficultyDialog = false
//                        onStartGameWithDifficulty("Easy")
//                    }) {
//                        Text("Easy")
//                    }
//
//                    Button(onClick = {
//                        showDifficultyDialog = false
//                        onStartGameWithDifficulty("Medium")
//                    }) {
//                        Text("Medium")
//                    }
//
//                    Button(onClick = {
//                        showDifficultyDialog = false
//                        onStartGameWithDifficulty("Hard")
//                    }) {
//                        Text("Hard")
//                    }
//                }
//            },
//            confirmButton = {},
//            dismissButton = {}
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun MenuScreenPreview() {
//    MenuScreen(
//        onStartGameWithDifficulty = {},
//        onViewScores = {},
//        onOpenSettings = {}
//    )
//}
