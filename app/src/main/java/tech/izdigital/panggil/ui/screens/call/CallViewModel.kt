package tech.izdigital.panggil.ui.screens.call

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CallViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CallUiState())
    val uiState: StateFlow<CallUiState> = _uiState.asStateFlow()
    init {
        // Load recent calls (dummy data for now)
        loadRecentCalls()
    }
    private fun loadRecentCalls() {
        val calls = listOf(
            RecentCall("Abah", "+60 13-702 6264", "Default", CallType.INCOMING),
            RecentCall("Fikri Amin", "+60 13-488 6163", "AmBank Default", CallType.OUTGOING),
            RecentCall("Afiq King (B-2-6)", "+60 13-938 2707", "Default", CallType.MISSED),
            RecentCall("Tuna GeneJunction", "+60 13-631 3602", "Default", CallType.OUTGOING),
        )
        _uiState.update {
            it.copy(recentCalls = calls, filteredCalls = calls)
        }
    }
    fun onNumberClick(digit: String) {
        val newNumber = _uiState.value.phoneNumber + digit
        _uiState.update {
            it.copy(
                phoneNumber = newNumber,
                filteredCalls = filterCalls(newNumber)
            )
        }
    }
    private fun filterCalls(query: String): List<RecentCall> {
        if (query.isEmpty()) return _uiState.value.recentCalls

        val normalizedQuery = query.replace(Regex("[^0-9+]"), "")
        return _uiState.value.recentCalls.filter { call ->
            val normalizedPhone = call.phoneNumber.replace(Regex("[^0-9+]"), "")
            normalizedPhone.contains(normalizedQuery)
        }
    }
    fun onBackspaceClick() {
        val newNumber = _uiState.value.phoneNumber.dropLast(1)
        _uiState.update {
            it.copy(
                phoneNumber = newNumber,
                filteredCalls = filterCalls(newNumber)
            )
        }
    }
    fun onBackspaceLongPress() {
        _uiState.update { currentState ->
            currentState.copy(phoneNumber = "")
        }
    }
    fun toggleNumpad() {
        _uiState.update { currentState ->
            currentState.copy(
                isNumpadVisible = !currentState.isNumpadVisible
            )
        }
    }
    fun showNumpad() {
        _uiState.update { currentState ->
            currentState.copy(isNumpadVisible = true)
        }
    }
    fun onNumberLongPress(digit: String) {
        // 0 long press = +
        // 1 long press = voicemail (we can skip this)
        val character = when(digit) {
            "0" -> "+"
            "1" -> "1" // Or could be voicemail shortcut
            "*" -> ","  // Pause
            "#" -> ";"  // Wait
            else -> digit
        }

        _uiState.update { currentState ->
            currentState.copy(
                phoneNumber = currentState.phoneNumber + character
            )
        }
    }
    fun onCallClick() {
        // We'll handle the actual call in the UI (Composable)
        // because it requires Context for Intent
        // ViewModel should not have Android Context dependencies
    }
}
data class CallUiState(
    val phoneNumber: String = "",
    val isLoading: Boolean = false,
    val isNumpadVisible: Boolean = true,
    val recentCalls: List<RecentCall> = emptyList(),
    val filteredCalls: List<RecentCall> = emptyList()
)

data class RecentCall(
    val name: String,
    val phoneNumber: String,
    val label: String,
    val type: CallType
)