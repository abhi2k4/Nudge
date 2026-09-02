package com.nudge.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nudge.feature.capture.CaptureScreen
import com.nudge.feature.context.ContextsScreen
import com.nudge.feature.home.HomeScreen
import com.nudge.feature.memory.MemoryScreen
import com.nudge.feature.nudges.NudgesScreen
import com.nudge.feature.tasks.TasksScreen
import com.nudge.feature.settings.SettingsScreen

// ─────────────────────────────────────────────────────────────────────────────
// Navigation destinations
// ─────────────────────────────────────────────────────────────────────────────

sealed class NudgeDestination(
    val route: String,
    val label: String,
    val icon: ImageVector,
) {
    object Home : NudgeDestination("home", "Home", Icons.Default.Home)
    object Capture : NudgeDestination("capture", "Capture", Icons.Default.Add)
    object Memory : NudgeDestination("memory", "Memory", Icons.Default.History)
    object Contexts : NudgeDestination("contexts", "Contexts", Icons.Default.AccountTree)
    object Tasks : NudgeDestination("tasks", "Tasks", Icons.Default.CheckBox)
    object Nudges : NudgeDestination("nudges", "Nudges", Icons.Default.Notifications)
    object Settings : NudgeDestination("settings", "Settings", Icons.Default.Settings)
}

private val bottomNavDestinations = listOf(
    NudgeDestination.Home,
    NudgeDestination.Capture,
    NudgeDestination.Memory,
    NudgeDestination.Tasks,
    NudgeDestination.Nudges,
)

// ─────────────────────────────────────────────────────────────────────────────
// Navigation graph
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavDestinations.forEach { destination ->
                    NavigationBarItem(
                        icon = { Icon(destination.icon, contentDescription = destination.label) },
                        label = { Text(destination.label) },
                        selected = currentDestination?.hierarchy?.any {
                            it.route == destination.route
                        } == true,
                        onClick = {
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    )
                }
            }
        },
    ) { _ ->
        NavHost(
            navController = navController,
            startDestination = NudgeDestination.Home.route,
        ) {
            composable(NudgeDestination.Home.route) { HomeScreen() }
            composable(NudgeDestination.Capture.route) { CaptureScreen() }
            composable(NudgeDestination.Memory.route) { MemoryScreen() }
            composable(NudgeDestination.Contexts.route) { ContextsScreen() }
            composable(NudgeDestination.Tasks.route) { TasksScreen() }
            composable(NudgeDestination.Nudges.route) { NudgesScreen() }
            composable(NudgeDestination.Settings.route) { SettingsScreen() }
        }
    }
}
