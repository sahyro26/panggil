package tech.izdigital.panggil.data.model

/**
 * Contact data model representing a contact in the app.
 * 
 * This model will be used for:
 * - Displaying contacts in the contacts list
 * - Storing favorite contacts in Room database
 * - Passing contact data between screens
 * 
 * TODO: Add Room annotations when implementing database
 * - @Entity(tableName = "contacts")
 * - @PrimaryKey
 * - @ColumnInfo
 */
data class Contact(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val photoUri: String? = null,
    val email: String? = null,
    val isFavorite: Boolean = false,
    val favoriteOrder: Int? = null,
    val favoriteAction: FavoriteAction? = null
)
