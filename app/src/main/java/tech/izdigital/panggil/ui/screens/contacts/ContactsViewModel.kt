// app/src/main/java/tech/izdigital/panggil/ui/screens/contacts/ContactsViewModel.kt
package tech.izdigital.panggil.ui.screens.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.izdigital.panggil.data.model.Contact
import tech.izdigital.panggil.data.repository.ContactsRepository

data class ContactsUiState(
    val contacts: List<Contact> = emptyList(),
    val filteredContacts: List<Contact> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val hasPermission: Boolean = false
)

class ContactsViewModel(
    private val repository: ContactsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ContactsUiState())
    val uiState: StateFlow<ContactsUiState> = _uiState.asStateFlow()

    fun loadContacts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val contacts = repository.loadContacts()
                _uiState.update {
                    it.copy(
                        contacts = contacts,
                        filteredContacts = contacts,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load contacts"
                    )
                }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update {
            val filtered = repository.searchContacts(it.contacts, query)
            it.copy(
                searchQuery = query,
                filteredContacts = filtered
            )
        }
    }

    fun onPermissionGranted() {
        _uiState.update { it.copy(hasPermission = true) }
        loadContacts()
    }

    fun onPermissionDenied() {
        _uiState.update { it.copy(hasPermission = false) }
    }
}
