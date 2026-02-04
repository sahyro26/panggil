package tech.izdigital.panggil.ui.screens.favorite

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel for Favorite Screen following MVVM architecture pattern.
 * 
 * Responsibilities:
 * - Manage UI state for favorite contacts
 * - Handle adding/removing favorites
 * - Manage favorite contact actions (call, message, video call, etc.)
 * - Handle reordering of favorites
 * - Persist favorites to Room database
 * 
 * The ViewModel survives configuration changes and provides
 * a clean separation between UI and business logic.
 */
class FavoriteViewModel : ViewModel() {
    // Private mutable state - only ViewModel can modify
    private val _uiState = MutableStateFlow(FavoriteUiState())
    
    // Public immutable state - UI observes this
    val uiState: StateFlow<FavoriteUiState> = _uiState.asStateFlow()
    
    // TODO: Add functions for:
    // - fun loadFavorites() - Load favorites from Room database
    // - fun addFavorite(contact: Contact) - Add contact to favorites
    // - fun removeFavorite(contactId: String) - Remove from favorites
    // - fun updateFavoriteAction(contactId: String, action: FavoriteAction) - Update action label
    // - fun reorderFavorites(from: Int, to: Int) - Reorder favorites
    // - fun onActionClick(contact: Contact, action: FavoriteAction) - Handle action clicks
    
    init {
        // TODO: Load favorites when ViewModel is created
        // loadFavorites()
    }
    
    /**
     * Clean up resources when ViewModel is destroyed
     */
    override fun onCleared() {
        super.onCleared()
        // TODO: Cancel any ongoing coroutines or cleanup resources
    }
}

/**
 * UI State for Favorite Screen.
 * Represents all the data that the UI needs to display.
 */
data class FavoriteUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
    // TODO: Add state properties:
    // - favorites: List<Contact> = emptyList()
    // - isEmpty: Boolean = true
)
