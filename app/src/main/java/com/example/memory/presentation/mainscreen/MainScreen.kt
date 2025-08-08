package com.example.memory.presentation.mainscreen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.memory.presentation.navigation.AppBottomNavGraph
import com.example.memory.presentation.navigation.BottomNavItem
import androidx.compose.ui.graphics.graphicsLayer
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem.Start,
        BottomNavItem.Score,
        BottomNavItem.Settings
    )
    var selectedItem by remember { mutableStateOf(0) }

    // 👇 observe current route
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // 👇 show bottom bar only on root tabs
    val showBottomBar = currentRoute in setOf(
        BottomNavItem.Start.route,
        BottomNavItem.Score.route,
        BottomNavItem.Settings.route
    )
    // Note: GameScreen composable is declared as "start/{difficulty}", so it won't match "start"

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                ) {
                    items.forEachIndexed { index, item ->
                        val scale by animateFloatAsState(if (selectedItem == index) 1.3f else 1f)
                        val verticalOffset by animateDpAsState(if (selectedItem == index) (-2).dp else 0.dp)

                        NavigationBarItem(
                            icon = {
                                Box(modifier = Modifier.size(52.dp), contentAlignment = Alignment.Center) {
                                    Image(
                                        painter = painterResource(id = item.iconRes),
                                        contentDescription = item.label,
                                        modifier = Modifier
                                            .size(45.dp)
                                            .offset(y = verticalOffset)
                                            .graphicsLayer {
                                                scaleX = scale
                                                scaleY = scale
                                            }
                                    )
                                }
                            },
                            label = { Text(item.label) },
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                indicatorColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.7f)
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            AppBottomNavGraph(navController = navController)
        }
    }
}
