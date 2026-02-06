package tech.izdigital.panggil.ui.screens.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tech.izdigital.panggil.data.model.Contact
import tech.izdigital.panggil.domain.PersonManager

class ContactsViewModel(private val personMgr: PersonManager) : ViewModel() {
    
    private val stateHolder = MutableStateFlow(DisplayConfiguration())
    val displayConfig: StateFlow<DisplayConfiguration> = stateHolder.asStateFlow()
    
    init {
        beginObservingPeople()
    }
    
    private fun beginObservingPeople() {
        viewModelScope.launch {
            personMgr.observePeople().collect { peopleList ->
                val currentConfig = stateHolder.value
                val toShow = when {
                    currentConfig.filterText.isNotBlank() -> applyTextFilter(peopleList, currentConfig.filterText)
                    else -> peopleList
                }
                stateHolder.value = currentConfig.copy(fullList = peopleList, visibleList = toShow)
            }
        }
    }
    
    fun switchDialogVisibility(makeVisible: Boolean) {
        stateHolder.value = stateHolder.value.copy(showingDialog = makeVisible)
    }
    
    fun addNewPerson(theName: String, thePhone: String, theMail: String?) {
        viewModelScope.launch {
            stateHolder.value = stateHolder.value.copy(busySaving = true, problemMessage = null)
            
            val result = personMgr.registerPerson(theName, thePhone, theMail)
            
            stateHolder.value = when {
                result.succeeded -> stateHolder.value.copy(busySaving = false, showingDialog = false, problemMessage = null)
                else -> stateHolder.value.copy(busySaving = false, problemMessage = (result as tech.izdigital.panggil.domain.Outcome.Failure).reason)
            }
        }
    }
    
    fun changeFilterText(newText: String) {
        val currentConfig = stateHolder.value
        val filteredResults = when {
            newText.isNotBlank() -> applyTextFilter(currentConfig.fullList, newText)
            else -> currentConfig.fullList
        }
        stateHolder.value = currentConfig.copy(filterText = newText, visibleList = filteredResults)
    }
    
    private fun applyTextFilter(people: List<Contact>, filterValue: String): List<Contact> {
        val normalized = filterValue.lowercase()
        return people.filter { person ->
            person.name.lowercase().contains(normalized) || person.phoneNumber.contains(filterValue)
        }
    }
    
    fun clearProblem() {
        stateHolder.value = stateHolder.value.copy(problemMessage = null)
    }
}

data class DisplayConfiguration(
    val fullList: List<Contact> = emptyList(),
    val visibleList: List<Contact> = emptyList(),
    val filterText: String = "",
    val showingDialog: Boolean = false,
    val busySaving: Boolean = false,
    val problemMessage: String? = null
)
