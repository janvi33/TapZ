package com.example.memory.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.memory.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    isDarkTheme: Boolean = false,
    isSoundEnabled: Boolean = true,
    onToggleTheme: (() -> Unit)? = null,
    onToggleSound: (() -> Unit)? = null,
    onLogoutConfirmed: (() -> Unit)? = null,
    onEditProfile: (() -> Unit)? = null,
    navController: NavController? = null
) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            // ✅ Only show back button if navController is provided
            if (navController != null) {
                TopAppBar(
                    title = { Text("Settings") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ChevronLeft, contentDescription = "Back")
                        }
                    }
                )
            } else {
                TopAppBar(title = { Text("Settings") })
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // 🧑‍💼 Edit Profile Row
            if (onEditProfile != null && navController != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onEditProfile)
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Edit Profile", modifier = Modifier.weight(1f))
                    Icon(Icons.Default.ChevronRight, contentDescription = "Edit Profile")
                }
            }

            // 🌗 Theme Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Dark Theme")
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = { onToggleTheme?.invoke() } // ✅ Safe call
                )
            }

            // 🔊 Sound Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Sound")
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = isSoundEnabled,
                    onCheckedChange = { onToggleSound?.invoke() } // ✅ Safe call
                )
            }

            // 🚪 Logout Button
            if (onLogoutConfirmed != null) {
                Button(
                    onClick = { showLogoutDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Logout")
                }
            }
        }
    }

    // 🧠 Logout Confirmation Dialog
    if (showLogoutDialog && onLogoutConfirmed != null) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Confirm Logout") },
            text = { Text("Are you sure you want to logout?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog = false
                        onLogoutConfirmed()
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
