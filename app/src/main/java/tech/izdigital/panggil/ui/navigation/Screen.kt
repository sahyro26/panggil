package tech.izdigital.panggil.ui.navigation

/**
 * Screen routes sealed class for type-safe navigation.
 * Each screen in the app has a unique route identifier.
 */
sealed class Screen(val route: String) {
    /**
     * Call screen - displays numpad and recent call history
     */
    data object Call : Screen("call")
    
    /**
     * Favorite screen - displays favorite contacts with custom actions
     */
    data object Favorite : Screen("favorite")
    
    /**
     * Contacts screen - displays contacts list or opens native contacts app
     */
    data object Contacts : Screen("contacts")
}
