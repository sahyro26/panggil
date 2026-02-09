// app/src/main/java/tech/izdigital/panggil/data/model/Favorite.kt
package tech.izdigital.panggil.data.model

data class Favorite(
    val id: Long = 0,
    val contactId: String,
    val contactName: String,
    val contactPhotoUri: String? = null,
    val actionType: FavoriteActionType,
    val actionData: String, // Phone number, email, WhatsApp number, etc.
    val position: Int = 0, // For reordering
    val createdAt: Long = System.currentTimeMillis()
)

enum class FavoriteActionType {
    CALL,
    MESSAGE,
    WHATSAPP_CALL,
    WHATSAPP_VIDEO_CALL,
    WHATSAPP_MESSAGE,
    TELEGRAM_CALL,
    TELEGRAM_MESSAGE,
    EMAIL
}
