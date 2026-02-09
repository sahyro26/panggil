package tech.izdigital.panggil.ui.screens.call

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

/**
 * Call Screen with overlay numpad design
 * Numpad appears on top of recent calls list
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CallScreen(
    viewModel: CallViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val callPermissionState = rememberPermissionState(
        permission = Manifest.permission.CALL_PHONE
    )

    Box(modifier = Modifier.fillMaxSize()) {

        // Background: Recent Calls List
        Column(modifier = Modifier.fillMaxSize()) {
            // Phone number display at top
            PhoneNumberDisplay(
                phoneNumber = uiState.phoneNumber,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            // Recent calls list (placeholder for now)
            RecentCallsList(
                calls = uiState.filteredCalls,
                searchQuery = uiState.phoneNumber,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Overlay: Numpad (animated)
        AnimatedVisibility(
            visible = uiState.isNumpadVisible,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it }),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            NumpadOverlay(
                onNumberClick = { digit -> viewModel.onNumberClick(digit) },
                onNumberLongPress = { digit -> viewModel.onNumberLongPress(digit) }, // NEW
                onBackspaceClick = { viewModel.onBackspaceClick() },
                onBackspaceLongPress = { viewModel.onBackspaceLongPress() },
                onMinimizeClick = { viewModel.toggleNumpad() },
                onCallClick = {
                    if (callPermissionState.status.isGranted) {
                        val intent = Intent(Intent.ACTION_CALL).apply {
                            data = Uri.parse("tel:${uiState.phoneNumber}")
                        }
                        context.startActivity(intent)
                    } else {
                        callPermissionState.launchPermissionRequest()
                    }
                },
                hasPhoneNumber = uiState.phoneNumber.isNotEmpty()
            )
        }

        // Floating button when numpad is hidden
        if (!uiState.isNumpadVisible) {
            FloatingNumpadButton(
                onClick = { viewModel.showNumpad() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            )
        }
    }
}

/**
 * Phone Number Display (simplified - no backspace here)
 */
@Composable
fun PhoneNumberDisplay(
    phoneNumber: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = phoneNumber.ifEmpty { "Enter number" },
        style = MaterialTheme.typography.displayMedium.copy(fontSize = 32.sp),
        color = if (phoneNumber.isEmpty())
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
        else
            MaterialTheme.colorScheme.onSurface,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

/**
 * Recent Calls List (placeholder)
 * TODO: We'll implement this next with real data
 */
@Composable
fun RecentCallsList(
    calls: List<RecentCall>,
    searchQuery: String,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(calls) { call ->
            RecentCallItem(
                call = call,
                searchQuery = searchQuery
            )
            Divider(color = MaterialTheme.colorScheme.outlineVariant)
        }
    }
}

/**
 * Recent Call Item
 */
@Composable
fun RecentCallItem(call: RecentCall, searchQuery: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = call.name.first().toString(),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Name and location
//        Column(modifier = Modifier.weight(1f)) {
//            Text(
//                text = call.name,
//                style = MaterialTheme.typography.bodyLarge,
//                fontWeight = FontWeight.Medium
//            )
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Icon(
//                    imageVector = when(call.type) {
//                        CallType.INCOMING -> Icons.Default.CallReceived
//                        CallType.OUTGOING -> Icons.Default.CallMade
//                        CallType.MISSED -> Icons.Default.CallMissed
//                    },
//                    contentDescription = null,
//                    modifier = Modifier.size(14.dp),
//                    tint = when(call.type) {
//                        CallType.MISSED -> Color(0xFFE53935)
//                        else -> MaterialTheme.colorScheme.onSurfaceVariant
//                    }
//                )
//                Spacer(modifier = Modifier.width(4.dp))
//                Text(
//                    text = "Malaysia",
//                    style = MaterialTheme.typography.bodySmall,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant
//                )
//            }
//        }

//        // Time
//        Text(
//            text = call.time,
//            style = MaterialTheme.typography.bodySmall,
//            color = MaterialTheme.colorScheme.onSurfaceVariant
//        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = call.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            // Highlighted phone number
            HighlightedPhoneNumber(
                phoneNumber = call.phoneNumber,
                searchQuery = searchQuery
            )
            Text(
                text = call.label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Info button
        IconButton(onClick = { /* TODO */ }) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Info",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Numpad Overlay - Covers bottom 2/3 of screen
 */
@Composable
fun NumpadOverlay(
    onNumberClick: (String) -> Unit,
    onNumberLongPress: (String) -> Unit,
    onBackspaceClick: () -> Unit,
    onBackspaceLongPress: () -> Unit,
    onMinimizeClick: () -> Unit,
    onCallClick: () -> Unit,
    hasPhoneNumber: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.67f) // Takes 2/3 of screen
            .background(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Row 1: 1, 2, 3
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                NumpadButtonItem("1", "",  onNumberClick, onNumberLongPress)
                NumpadButtonItem("2", "ABC",  onNumberClick, onNumberLongPress)
                NumpadButtonItem("3", "DEF",  onNumberClick, onNumberLongPress)
            }
            // Row 2: 4, 5, 6
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                NumpadButtonItem("4", "GHI",  onNumberClick, onNumberLongPress)
                NumpadButtonItem("5", "JKL",  onNumberClick, onNumberLongPress)
                NumpadButtonItem("6", "MNO",  onNumberClick, onNumberLongPress)
            }
            // Row 3: 7, 8, 9
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                NumpadButtonItem("7", "PQRS",  onNumberClick, onNumberLongPress)
                NumpadButtonItem("8", "TUV",  onNumberClick, onNumberLongPress)
                NumpadButtonItem("9", "WXYZ",  onNumberClick, onNumberLongPress)
            }
            // Row 4: *, 0, #
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                NumpadButtonItem("*", "",  onNumberClick, onNumberLongPress)
                NumpadButtonItem("0", "+",  onNumberClick, onNumberLongPress)
                NumpadButtonItem("#", "",  onNumberClick, onNumberLongPress)
            }
        }


        Spacer(modifier = Modifier.height(0.dp))

        // Bottom action buttons: [Minimize] [Call] [Delete]
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: Minimize button
            IconButton(
                onClick = onMinimizeClick,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreHoriz,
                    contentDescription = "Minimize",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            // Center: Call button (GREEN and LARGE)
            FilledIconButton(
                onClick = onCallClick,
                enabled = hasPhoneNumber,
                modifier = Modifier.size(64.dp),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Color(0xFF4CAF50), // Green like image
                    contentColor = Color.White,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Call",
                    modifier = Modifier.size(32.dp)
                )
            }

            // Right: Delete button
            IconButton(
                onClick = onBackspaceClick,
                modifier = Modifier.size(56.dp),
                enabled = hasPhoneNumber
            ) {
                Icon(
                    imageVector = Icons.Default.Backspace,
                    contentDescription = "Delete",
                    tint = if (hasPhoneNumber)
                        MaterialTheme.colorScheme.onSurface
                    else
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                )
            }
        }
//        Spacer(modifier = Modifier.height(8.dp))
    }
}

/**
 * Numpad Button Item (same as before)
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NumpadButtonItem(
    number: String,
    letters: String,
    onClick: (String) -> Unit,
    onLongClick: (String) -> Unit
) {
    val haptic = LocalHapticFeedback.current

    OutlinedButton(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick(number)
        },
        modifier = Modifier.size(64.dp)
            .combinedClickable(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onClick(number)
                },
                onLongClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onLongClick(letters)
                }
            ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = CircleShape,
        border = null,
        contentPadding = PaddingValues(0.dp)// Remove border for cleaner look
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = number,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            if (letters.isNotEmpty()) {
                Text(
                    text = letters,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

/**
 * Floating button when numpad is minimized
 */
@Composable
fun FloatingNumpadButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        Icon(
            imageVector = Icons.Default.Dialpad,
            contentDescription = "Show numpad"
        )
    }
}

// New composable to highlight matching digits
@Composable
fun HighlightedPhoneNumber(
    phoneNumber: String,
    searchQuery: String
) {
    if (searchQuery.isEmpty()) {
        Text(
            text = phoneNumber,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
        return
    }

    val normalizedQuery = searchQuery.replace(Regex("[^0-9+]"), "")
    val normalizedPhone = phoneNumber.replace(Regex("[^0-9+]"), "")
    val startIndex = normalizedPhone.indexOf(normalizedQuery)

    if (startIndex == -1) {
        Text(
            text = phoneNumber,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF4CAF50)
        )
        return
    }

    // Map back to original phone string with formatting
    val parts = mutableListOf<Pair<String, Boolean>>()
    var originalIndex = 0
    var normalizedIndex = 0

    phoneNumber.forEach { char ->
        val isDigit = char.isDigit() || char == '+'
        val isHighlighted = if (isDigit) {
            normalizedIndex in startIndex until (startIndex + normalizedQuery.length)
        } else false

        parts.add(char.toString() to isHighlighted)

        if (isDigit) normalizedIndex++
    }

    // Build annotated string
    Row {
        parts.forEach { (char, highlighted) ->
            Text(
                text = char,
                style = MaterialTheme.typography.bodyMedium,
                color = if (highlighted) Color(0xFF4CAF50)
                else MaterialTheme.colorScheme.primary,
                fontWeight = if (highlighted) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

// Data models for recent calls

enum class CallType {
    INCOMING,
    OUTGOING,
    MISSED
}

data class NumpadButton(
    val number: String,
    val letters: String
)