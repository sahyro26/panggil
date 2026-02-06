package tech.izdigital.panggil.data.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "people_records")
data class PersonRecord(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "record_id")
    val recordId: Long = 0,
    
    @ColumnInfo(name = "person_name")
    val personName: String,
    
    @ColumnInfo(name = "phone_digits")
    val phoneDigits: String,
    
    @ColumnInfo(name = "mail_address")
    val mailAddress: String? = null,
    
    @ColumnInfo(name = "picture_path")
    val picturePath: String? = null,
    
    @ColumnInfo(name = "added_manually")
    val addedManually: Boolean = true,
    
    @ColumnInfo(name = "creation_time")
    val creationTime: Long = System.currentTimeMillis()
)
