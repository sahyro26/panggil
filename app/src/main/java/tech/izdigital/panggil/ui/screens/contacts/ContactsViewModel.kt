package tech.izdigital.panggil.ui.screens.contacts

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel for Contacts Screen following MVVM architecture pattern.
 * 
 * Responsibilities:
 * - Manage UI state for contacts screen
 * - Load contacts from device
 * - Handle contact selection
 * - Search/filter contacts
 * - Request contacts permission
 * 
 * The ViewModel survives configuration changes and provides
 * a clean separation between UI and business logic.
 */
class ContactsViewModel : ViewModel() {
    // Private mutable state - only ViewModel can modify
    private val _uiState = MutableStateFlow(ContactsUiState())
    
    // Public immutable state - UI observes this
    val uiState: StateFlow<ContactsUiState> = _uiState.asStateFlow()
    
    // TODO: Add functions for:
    // - fun loadContacts() - Load contacts from device
    // - fun searchContacts(query: String) - Search/filter contacts
    // - fun onContactSelected(contact: Contact) - Handle contact selection
    // - fun requestContactsPermission() - Handle permission request
    // - fun openNativeContactsApp() - Open system contacts app
    
    init {
        // TODO: Load contacts when ViewModel is created (after permission check)
        // checkPermissionAndLoadContacts()
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
 * UI State for Contacts Screen.
 * Represents all the data that the UI needs to display.
 */
data class ContactsUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val hasContactsPermission: Boolean = false
    // TODO: Add state properties:
    // - contacts: List<Contact> = emptyList()
    // - searchQuery: String = ""
    // - filteredContacts: List<Contact> = emptyList()
)
