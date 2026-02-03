package tech.izdigital.panggil.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Bottom navigation item data class.
 * Defines the properties needed for each bottom navigation tab.
 */
data class BottomNavItem(
    val screen: Screen,
    val icon: ImageVector,
    val label: String
)

/**
 * List of all bottom navigation items in the app.
 * This defines the tabs that appear in the bottom navigation bar.
 */
val bottomNavItems = listOf(
    BottomNavItem(
        screen = Screen.Call,
        icon = Icons.Filled.Call,
        label = "Call"
    ),
    BottomNavItem(
        screen = Screen.Favorite,
        icon = Icons.Filled.Star,
        label = "Favorite"
    ),
    BottomNavItem(
        screen = Screen.Contacts,
        icon = Icons.Filled.Person,
        label = "Contacts"
    )
)
