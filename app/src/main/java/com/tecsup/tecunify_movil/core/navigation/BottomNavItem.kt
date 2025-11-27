package com.tecsup.tecunify_movil.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem("home_tab", "Inicio", Icons.Filled.Home)
    object Spaces : BottomNavItem("spaces_tab", "Espacios", Icons.Filled.GridView)
    object Reservations :
        BottomNavItem("reservations_tab", "Mis reservas", Icons.AutoMirrored.Filled.ListAlt)
    object Schedule :
        BottomNavItem("schedule_tab", "Horarios", Icons.Filled.CalendarMonth)
    object Chat : BottomNavItem("chat_tab", "TeclA Chat", Icons.Filled.ChatBubbleOutline)
}