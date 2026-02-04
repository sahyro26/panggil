package tech.izdigital.panggil.ui.screens.contacts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Contacts Screen - Display all contacts or open native contacts app.
 * 
 * This screen has two possible implementations:
 * 
 * Option 1: Display contacts list within the app
 * - Load contacts from device using ContentProvider
 * - Display in scrollable list with search
 * - Show contact photo, name, and phone number(s)
 * - Click to call or add to favorites
 * 
 * Option 2: Open native contacts app
 * - Simple button to launch system contacts app
 * - Cleaner approach, leverages existing contacts UI
 * - Less maintenance, uses system UI/UX
 * 
 * Architecture:
 * - Uses ContactsViewModel for state management (MVVM pattern)
 * - Observes UI state changes via StateFlow
 * - Handles runtime permissions for READ_CONTACTS
 * - All business logic is handled in the ViewModel
 * 
 * @param viewModel The ViewModel that manages state for this screen
 */
@Composable
fun ContactsScreen(
    viewModel: ContactsViewModel = viewModel()
) {
    // TODO: Collect UI state
    // val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Placeholder content
        Text(
            text = "Contacts Screen",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        
        Text(
            text = "Native contacts integration",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
        
        // TODO: Implement Permission Handling
        // - Check READ_CONTACTS permission
        // - Request permission if not granted
        // - Show permission rationale if needed
        // - Use Accompanist Permissions library
        
        // TODO: Option 1 - In-app Contacts List
        // - Load contacts using ContentResolver
        // - Display in LazyColumn with FastScrollbar
        // - Search bar at top for filtering
        // - Section headers (A, B, C, etc.)
        // - Contact item: Photo, Name, Phone number(s)
        // - Click actions: Call, Message, Add to Favorites
        
        // TODO: Option 2 - Open Native Contacts App
        // - Button: "Open Contacts"
        // - OnClick: Launch system contacts app
        // - Intent: Intent.ACTION_VIEW with ContactsContract.Contacts.CONTENT_URI
        // - Simpler implementation, better UX
    }
}
