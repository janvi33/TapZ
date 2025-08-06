package com.example.memory.presentation.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    state: SettingsState,
    onEvent: (SettingsIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Settings", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Dark Theme", modifier = Modifier.weight(1f))
            Switch(
                checked = state.isDarkTheme,
                onCheckedChange = { onEvent(SettingsIntent.ToggleTheme) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Sound", modifier = Modifier.weight(1f))
            Switch(
                checked = state.isSoundOn,
                onCheckedChange = { onEvent(SettingsIntent.ToggleSound) }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { onEvent(SettingsIntent.LogoutClicked) }) {
            Text("Logout")
        }
    }
}
