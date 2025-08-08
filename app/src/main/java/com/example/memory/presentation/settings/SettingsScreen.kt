package com.example.memory.presentation.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.memory.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    // Optional external state/callbacks; if null, we fall back to ViewModel
    isDarkTheme: Boolean? = null,
    isSoundEnabled: Boolean? = null,
    onToggleTheme: (() -> Unit)? = null,
    navController: NavController? = null,
    onToggleSound: (() -> Unit)? = null,
    onLogoutConfirmed: () -> Unit,
    onEditProfile: (() -> Unit)? = null,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    // Use VM state by default; if callers pass their own values, prefer those
    val vmState = viewModel.state
    val dark = isDarkTheme ?: vmState.isDarkTheme
    val sound = isSoundEnabled ?: vmState.isSoundOn

    var showLogoutDialog by remember { mutableStateOf(false) }

    // If VM sets isLoggedOut = true, notify caller (navigate, etc.), then reset flag
    LaunchedEffect(vmState.isLoggedOut) {
        if (vmState.isLoggedOut) {
            onLogoutConfirmed.invoke()
            viewModel.resetLogoutFlag()
        }
    }


    Scaffold(
        topBar = {
            SettingsTopBarLikeGame(
                title = "Settings",
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Profile
            SectionCardOrangeTint {
                SettingsRow(
                    icon = { Icon(Icons.Outlined.AccountCircle, contentDescription = null) },
                    title = "Edit Profile",
                    trailing = { Icon(Icons.Filled.ChevronRight, contentDescription = null) },
                    enabled = (onEditProfile != null),
                    onClick = { onEditProfile?.invoke() }
                )
            }

            // Preferences
            SectionCardOrangeTint(header = "Preferences") {
                ToggleRow(
                    icon = { Icon(Icons.Outlined.DarkMode, contentDescription = null) },
                    title = "Dark Theme",
                    checked = dark,
                    onCheckedChange = {
                        // Prefer external callback if provided, else VM intent
                        onToggleTheme?.invoke() ?: viewModel.onEvent(SettingsIntent.ToggleTheme)
                    }
                )
                Divider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant)
                ToggleRow(
                    icon = { Icon(Icons.Outlined.MusicNote, contentDescription = null) },
                    title = "Sound",
                    checked = sound,
                    onCheckedChange = {
                        onToggleSound?.invoke() ?: viewModel.onEvent(SettingsIntent.ToggleSound)
                    }
                )
            }

            // Logout
            // Logout
            // Logout
            SectionCardOrangeTint {
                Button(
                    onClick = { showLogoutDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF5722), // Fire orange-red
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp) // optional to make it uniform height
                ) {
                    Icon(
                        Icons.Outlined.Logout,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Logout",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }




            Spacer(Modifier.height(8.dp))
        }
    }

    // Confirm only when external handler is provided (so you can navigate)
    // Show regardless of onLogoutConfirmed being null
    // Show the dialog regardless of onLogoutConfirmed being null
    if (showLogoutDialog) {
        ConfirmLogoutDialog(
            onConfirm = {
                // 1) Sign out via VM (Firebase + set isLoggedOut = true)
                viewModel.onEvent(SettingsIntent.LogoutClicked)

                // 2) Close dialog
                showLogoutDialog = false

                // 3) Optionally let caller navigate (e.g., to Login)
                onLogoutConfirmed.invoke()
            },
            onDismiss = { showLogoutDialog = false }
        )
    }


}

/* ==================== Top Bar (compact, theme-aware, orange accent line) ==================== */
@Composable
private fun SettingsTopBarLikeGame(
    title: String,
) {
    val peach = Brush.horizontalGradient(listOf(Color(0xFFF5DABE), Color(0xFFFDC983)))
    val border = Brush.linearGradient(listOf(Color(0x22FFB74D), Color(0x11FF8A65)))
    val underline = Color(0xFFFFA726)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 14.dp) // a bit of breathing room from status bar
    ) {
        // thin orange underline for consistency


        Surface(
            color = Color.Transparent,
            shape = RoundedCornerShape(20.dp),
            tonalElevation = 0.dp,
            shadowElevation = 0.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 14.dp)
                .background(peach, RoundedCornerShape(20.dp))
                .border(1.dp, border, RoundedCornerShape(20.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left: bold app section title (matches your TAP-Z vibe)
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                    color = Color(0xFF3E2723),
                    modifier = Modifier.padding(end = 14.dp)
                )

                // Right: compact “pills” like game screen (tap to toggle)
            }
        }
    }
}


/* ==================== Building Blocks ==================== */

@Composable
private fun SectionCardOrangeTint(
    header: String? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val border = Brush.linearGradient(
        listOf(
            Color(0x22FFB74D), // very light orange alpha
            Color(0x11FF8A65)
        )
    )
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        if (header != null) {
            Text(
                text = header,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
        ElevatedCard(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
            ),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, border, RoundedCornerShape(18.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                content = content
            )
        }
    }
}

@Composable
private fun SettingsRow(
    icon: @Composable (() -> Unit)? = null,
    title: String,
    trailing: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val contentColor = if (enabled) MaterialTheme.colorScheme.onSurface
    else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 52.dp)
            .clickable(enabled = enabled, onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Box(Modifier.size(24.dp), contentAlignment = Alignment.Center) { icon() }
            Spacer(Modifier.width(12.dp))
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = contentColor,
            modifier = Modifier.weight(1f)
        )
        if (trailing != null) {
            Spacer(Modifier.width(8.dp))
            trailing()
        }
    }
}

@Composable
private fun ToggleRow(
    icon: @Composable (() -> Unit)? = null,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 52.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Box(Modifier.size(24.dp), contentAlignment = Alignment.Center) { icon() }
            Spacer(Modifier.width(12.dp))
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
        // Orange-tinted switch to match the section accent
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                uncheckedTrackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)
            )
        )
    }
}

/* ==================== Logout Dialog ==================== */

@Composable
private fun ConfirmLogoutDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val fireOrange = Color(0xFFFF5722)
    val fireOrangeLight = Color(0xFFFF8A50)

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = fireOrange,
                    contentColor = Color.White
                )
            ) {
                Text("Logout")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) { Text("Cancel") }
        },
        title = { Text("Confirm Logout", style = MaterialTheme.typography.titleLarge) },
        text = {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .background(
                            Brush.horizontalGradient(listOf(fireOrangeLight, fireOrange)),
                            shape = RoundedCornerShape(2.dp)
                        )
                )
                Spacer(Modifier.height(12.dp))
                Text("Are you sure you want to logout?")
            }
        },
        shape = RoundedCornerShape(24.dp),
        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        tonalElevation = 8.dp
    )
}
