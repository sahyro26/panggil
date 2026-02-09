package tech.izdigital.panggil.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import tech.izdigital.panggil.data.repository.ContactsRepository
import tech.izdigital.panggil.ui.screens.call.CallScreen
import tech.izdigital.panggil.ui.screens.contacts.ContactsScreen
import tech.izdigital.panggil.ui.screens.contacts.ContactsViewModel
import tech.izdigital.panggil.ui.screens.contacts.ContactsViewModelFactory
import tech.izdigital.panggil.ui.screens.favorite.FavoriteScreen

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
        composable(route = Screen.Call.route) {
            CallScreen()
        }

        composable(route = Screen.Favorite.route) {
            FavoriteScreen()
        }

        composable(route = Screen.Contacts.route) {
            val context = LocalContext.current
            val viewModel: ContactsViewModel = viewModel(
                factory = ContactsViewModelFactory(
                    repository = ContactsRepository(context)
                )
            )
            ContactsScreen(
                viewModel = viewModel,
                onCallContact = { phoneNumber ->
                    // TODO: Handle call action
                    // You can navigate to call screen or trigger dialer
                }
            )
        }
    }
}

