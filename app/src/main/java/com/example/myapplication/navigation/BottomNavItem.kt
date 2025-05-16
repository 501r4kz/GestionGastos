package com.example.myapplication.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val title: String
) {
    object Home : BottomNavItem("home", Icons.Filled.Home, "Inicio")
    object Chart : BottomNavItem("chart", Icons.Filled.Person, "Gráfica")
    object Settings : BottomNavItem("settings", Icons.Filled.Settings, "Configuración")
}
