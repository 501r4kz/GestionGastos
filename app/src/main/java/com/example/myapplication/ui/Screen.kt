// Screen.kt
package com.example.myapplication.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Home : Screen("home", "Inicio", Icons.Default.Home)
    object Chart : Screen("chart", "Gráfica", Icons.Default.Person)
    object Settings : Screen("settings", "Configuración", Icons.Default.Settings)
}
