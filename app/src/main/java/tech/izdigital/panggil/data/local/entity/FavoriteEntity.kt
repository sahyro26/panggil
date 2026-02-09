// app/src/main/java/tech/izdigital/panggil/data/local/entity/FavoriteEntity.kt
package tech.izdigital.panggil.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val contactId: String,
    val contactName: String,
    val contactPhotoUri: String?,
    val actionType: String, // Store enum as string
    val actionData: String,
    val position: Int,
    val createdAt: Long
)
