package tech.izdigital.panggil.ui.screens.favorite

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
 * Favorite Screen - Display favorite contacts with custom actions (iPhone-like).
 * 
 * This screen will contain:
 * - Grid/List of favorite contacts with profile pictures
 * - Custom action labels for each favorite contact
 * - Quick action buttons (Call, Message, Video, WhatsApp variants)
 * - Add/Remove favorite functionality
 * - Reorder favorites (drag & drop)
 * 
 * Action types available:
 * - Call (regular phone call)
 * - Message (SMS)
 * - Video Call (regular video call)
 * - WhatsApp Call (voice)
 * - WhatsApp Message (chat)
 * - WhatsApp Video Call
 * 
 * Architecture:
 * - Uses FavoriteViewModel for state management (MVVM pattern)
 * - Observes UI state changes via StateFlow
 * - Favorites persisted in Room database
 * - All business logic is handled in the ViewModel
 * 
 * @param viewModel The ViewModel that manages state for this screen
 */
@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = viewModel()
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
            text = "Favorite Screen",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        
        Text(
            text = "Favorite contacts with custom actions",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
        
        // TODO: Implement Favorite Contacts List/Grid
        // - Display favorite contacts in a grid (2-3 columns)
        // - Show contact photo, name, and custom action label
        // - Empty state: "Add your favorite contacts" with + button
        
        // TODO: Implement Quick Actions
        // - Show action buttons for each favorite
        // - Icons for: Phone, Message, Video, WhatsApp variants
        // - OnClick: Execute the appropriate action
        // - Long press: Edit action label/type
        
        // TODO: Implement Add/Remove Favorites
        // - FAB or header button to add new favorites
        // - Opens contact picker
        // - Lets user select action type during add
        // - Swipe or long press to remove
        
        // TODO: Implement Reordering
        // - Drag and drop to reorder favorites
        // - Save order to database
        // - Use LazyVerticalGrid with draggable items
    }
}
