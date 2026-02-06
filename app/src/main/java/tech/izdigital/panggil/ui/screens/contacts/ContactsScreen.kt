package tech.izdigital.panggil.ui.screens.contacts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tech.izdigital.panggil.data.model.Contact
import tech.izdigital.panggil.domain.PersonManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen(
    contactsVm: ContactsViewModel = run {
        val appCtx = LocalContext.current
        val mgr = PersonManager(appCtx)
        ContactsViewModel(mgr)
    }
) {
    val config by contactsVm.displayConfig.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contacts") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { contactsVm.switchDialogVisibility(true) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add contact")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            FilterField(
                currentText = config.filterText,
                onTextChange = contactsVm::changeFilterText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            
            when {
                config.visibleList.isEmpty() && config.filterText.isBlank() -> {
                    EmptyPlaceholder()
                }
                config.visibleList.isEmpty() -> {
                    NoResultsPlaceholder()
                }
                else -> {
                    PeopleList(
                        people = config.visibleList,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
    
    if (config.showingDialog) {
        AddPersonDialog(
            onDismiss = { contactsVm.switchDialogVisibility(false) },
            onConfirm = { name, phone, email ->
                contactsVm.addNewPerson(name, phone, email)
            },
            isBusy = config.busySaving,
            errorText = config.problemMessage,
            onErrorDismiss = contactsVm::clearProblem
        )
    }
}

@Composable
private fun FilterField(
    currentText: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = currentText,
        onValueChange = onTextChange,
        modifier = modifier,
        placeholder = { Text("Filter contacts...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        singleLine = true,
        shape = MaterialTheme.shapes.large
    )
}

@Composable
private fun PeopleList(
    people: List<Contact>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(people, key = { it.id }) { person ->
            PersonCard(person)
        }
    }
}

@Composable
private fun PersonCard(person: Contact) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = person.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = person.phoneNumber,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            IconButton(onClick = { /* TODO: Call action */ }) {
                Icon(
                    Icons.Default.Phone,
                    contentDescription = "Call",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun EmptyPlaceholder() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.outline
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No contacts yet",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Tap + to add your first contact",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
private fun NoResultsPlaceholder() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No matching contacts",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun AddPersonDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, String?) -> Unit,
    isBusy: Boolean,
    errorText: String?,
    onErrorDismiss: () -> Unit
) {
    var nameInput by remember { mutableStateOf("") }
    var phoneInput by remember { mutableStateOf("") }
    var emailInput by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Contact") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    label = { Text("Name *") },
                    singleLine = true,
                    enabled = !isBusy
                )
                OutlinedTextField(
                    value = phoneInput,
                    onValueChange = { phoneInput = it },
                    label = { Text("Phone *") },
                    singleLine = true,
                    enabled = !isBusy
                )
                OutlinedTextField(
                    value = emailInput,
                    onValueChange = { emailInput = it },
                    label = { Text("Email (optional)") },
                    singleLine = true,
                    enabled = !isBusy
                )
                
                if (errorText != null) {
                    Text(
                        text = errorText,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (nameInput.isNotBlank() && phoneInput.isNotBlank()) {
                        onConfirm(
                            nameInput,
                            phoneInput,
                            emailInput.ifBlank { null }
                        )
                    }
                },
                enabled = !isBusy && nameInput.isNotBlank() && phoneInput.isNotBlank()
            ) {
                if (isBusy) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp))
                } else {
                    Text("Add")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !isBusy) {
                Text("Cancel")
            }
        }
    )
}
