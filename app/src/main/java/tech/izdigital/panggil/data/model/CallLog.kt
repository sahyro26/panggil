package tech.izdigital.panggil.data.model

import java.util.Date

/**
 * CallLog data model representing a call log entry.
 * 
 * This model represents call history from the device's call log.
 * Used for displaying recent calls on the Call screen.
 * 
 * Call types:
 * - INCOMING: Received call
 * - OUTGOING: Made call
 * - MISSED: Missed call
 * - REJECTED: Rejected call
 * - BLOCKED: Blocked call
 */
data class CallLog(
    val id: String,
    val contactName: String?,
    val phoneNumber: String,
    val callType: CallType,
    val timestamp: Date,
    val duration: Long, // in seconds
    val photoUri: String? = null
)

/**
 * Enum representing different types of calls
 */
enum class CallType {
    INCOMING,
    OUTGOING,
    MISSED,
    REJECTED,
    BLOCKED
}
