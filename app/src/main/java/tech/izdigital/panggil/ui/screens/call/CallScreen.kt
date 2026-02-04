package tech.izdigital.panggil.ui.screens.call

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
 * Call Screen - Main dialer screen with numpad and recent calls.
 * 
 * This screen will contain:
 * - Phone number display area
 * - Numpad (0-9, *, #)
 * - Call button
 * - Delete/Clear buttons
 * - Recent calls list (incoming, outgoing, missed)
 * 
 * Architecture:
 * - Uses CallViewModel for state management (MVVM pattern)
 * - Observes UI state changes via StateFlow
 * - All business logic is handled in the ViewModel
 * 
 * @param viewModel The ViewModel that manages state for this screen
 */
@Composable
fun CallScreen(
    viewModel: CallViewModel = viewModel()
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
            text = "Call Screen",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        
        Text(
            text = "Numpad and Recent Calls will appear here",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
        
        // TODO: Implement Numpad Layout
        // - Display phone number input field
        // - Numpad grid (3x4) with digits 0-9, *, #
        // - Backspace/Delete button
        // - Clear all button
        
        // TODO: Implement Call Button
        // - Large circular call button (green)
        // - Only enabled when phone number is valid
        // - onClick: viewModel.onCallButtonClick()
        
        // TODO: Implement Recent Calls List
        // - Display recent calls from call log
        // - Show contact name/number, call type icon, time
        // - Call types: Incoming (blue), Outgoing (green), Missed (red)
        // - onClick: populate numpad with selected number
        // - Swipe to delete/callback
    }
}
