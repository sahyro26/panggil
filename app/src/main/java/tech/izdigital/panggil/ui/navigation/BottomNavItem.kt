package tech.izdigital.panggil.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.ui.graphics.vector.ImageVector
data class BottomNavItem(
    val screen: Screen,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector,
    val label: String
)
val bottomNavItems = listOf(
    BottomNavItem(
        screen = Screen.Call,
        filledIcon = Icons.Filled.Call,
        outlinedIcon = Icons.Outlined.Call,
        label = "Call"
    ),
    BottomNavItem(
        screen = Screen.Favorite,
        filledIcon = Icons.Filled.Star,
        outlinedIcon = Icons.Outlined.StarBorder,
        label = "Favorite"
    ),
    BottomNavItem(
        screen = Screen.Contacts,
        filledIcon = Icons.Filled.Person,
        outlinedIcon = Icons.Outlined.Person,
        label = "Contacts"
    )
)
