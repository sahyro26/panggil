package tech.izdigital.panggil.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import tech.izdigital.panggil.ui.screens.call.CallScreen
import tech.izdigital.panggil.ui.screens.contacts.ContactsScreen
import tech.izdigital.panggil.ui.screens.favorite.FavoriteScreen

/**
 * Navigation graph for the app using Jetpack Navigation Compose.
 * 
 * This composable sets up the navigation structure with:
 * - NavHost as the container for navigation
 * - Composable destinations for each screen
 * - Starting destination (Call screen)
 * 
 * @param navController The navigation controller to handle navigation actions
 * @param modifier Modifier to be applied to the NavHost
 */
@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Call.route,
        modifier = modifier
    ) {
        // Call screen destination
        composable(route = Screen.Call.route) {
            CallScreen()
        }
        
        // Favorite screen destination
        composable(route = Screen.Favorite.route) {
            FavoriteScreen()
        }
        
        // Contacts screen destination
        composable(route = Screen.Contacts.route) {
            ContactsScreen()
        }
    }
}
