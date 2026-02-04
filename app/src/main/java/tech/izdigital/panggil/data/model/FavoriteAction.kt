package tech.izdigital.panggil.data.model

/**
 * FavoriteAction enum representing different action types for favorite contacts.
 * 
 * iPhone-like favorite actions that can be assigned to each favorite contact.
 * Each favorite contact can have one action type assigned.
 * 
 * Action Types:
 * - CALL: Regular phone call
 * - MESSAGE: Send SMS message
 * - VIDEO_CALL: Video call using native video calling
 * - WHATSAPP_CALL: Voice call via WhatsApp
 * - WHATSAPP_MESSAGE: Send message via WhatsApp
 * - WHATSAPP_VIDEO: Video call via WhatsApp
 * 
 * Usage:
 * When user taps on a favorite contact, the app will execute the assigned action.
 * User can change the action type by long-pressing the favorite contact.
 */
enum class FavoriteAction(val displayName: String) {
    CALL("Call"),
    MESSAGE("Message"),
    VIDEO_CALL("Video Call"),
    WHATSAPP_CALL("WhatsApp Call"),
    WHATSAPP_MESSAGE("WhatsApp Message"),
    WHATSAPP_VIDEO("WhatsApp Video");
    
    companion object {
        /**
         * Get FavoriteAction from display name
         */
        fun fromDisplayName(name: String): FavoriteAction? {
            return entries.find { it.displayName == name }
        }
    }
}
