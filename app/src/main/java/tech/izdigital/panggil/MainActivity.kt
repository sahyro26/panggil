package tech.izdigital.panggil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import tech.izdigital.panggil.ui.navigation.NavigationGraph
import tech.izdigital.panggil.ui.navigation.bottomNavItems
import tech.izdigital.panggil.ui.theme.PanggilTheme

/**
 * MainActivity - The main entry point of the Panggil dialer app.
 * 
 * This activity sets up:
 * - Jetpack Compose UI with Material3 theme
 * - Bottom navigation bar with 3 tabs (Call, Favorite, Contacts)
 * - Navigation graph using Jetpack Navigation Compose
 * - MVVM architecture pattern with ViewModels
 * 
 * Architecture Notes:
 * - Uses Scaffold for Material3 layout structure
 * - Navigation state is preserved across configuration changes
 * - Bottom navigation provides main app navigation
 * - Each screen has its own ViewModel for state management
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PanggilTheme {
                MainScreen()
            }
        }
    }
}

/**
 * Main screen composable with bottom navigation and navigation host.
 * 
 * Features:
 * - Bottom navigation bar with 3 tabs
 * - Selected state highlighting
 * - Smooth navigation transitions
 * - Navigation state preservation
 * - Single top navigation (prevents duplicate screens in back stack)
 */
@Composable
fun MainScreen() {
    // NavController manages app navigation
    val navController = rememberNavController()
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                // Get current navigation destination
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                
                // Create navigation bar items
                bottomNavItems.forEach { item ->
                    // compute selected state once
                    val selected = currentDestination?.hierarchy?.any {
                        it.route == item.screen.route
                    } == true

                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = if (selected) item.filledIcon else item.outlinedIcon,
                                contentDescription = item.label
                            )
                        },
                        label = { Text(item.label) },
                        selected = selected,
                        onClick = {
                            navController.navigate(item.screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        // Navigation host contains all screen destinations
        NavigationGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
