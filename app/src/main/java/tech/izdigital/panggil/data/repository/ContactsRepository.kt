// app/src/main/java/tech/izdigital/panggil/data/repository/ContactsRepository.kt
package tech.izdigital.panggil.data.repository

import android.content.ContentResolver
import android.content.Context
import android.provider.ContactsContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tech.izdigital.panggil.data.model.Contact
import tech.izdigital.panggil.data.model.PhoneNumber
import tech.izdigital.panggil.data.model.PhoneType

class ContactsRepository(private val context: Context) {

    suspend fun loadContacts(): List<Contact> = withContext(Dispatchers.IO) {
        val contacts = mutableMapOf<String, MutableList<PhoneNumber>>()
        val contactsInfo = mutableMapOf<String, Pair<String, String?>>()

        val contentResolver: ContentResolver = context.contentResolver

        // Query phone numbers
        val phoneCursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.TYPE,
                ContactsContract.CommonDataKinds.Phone.PHOTO_URI
            ),
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )

        phoneCursor?.use { cursor ->
            val idIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val typeIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE)
            val photoIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)

            while (cursor.moveToNext()) {
                val id = cursor.getString(idIndex)
                val name = cursor.getString(nameIndex) ?: "Unknown"
                val number = cursor.getString(numberIndex) ?: continue
                val type = cursor.getInt(typeIndex)
                val photoUri = cursor.getString(photoIndex)

                contactsInfo[id] = name to photoUri

                val phoneType = when (type) {
                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE -> PhoneType.MOBILE
                    ContactsContract.CommonDataKinds.Phone.TYPE_HOME -> PhoneType.HOME
                    ContactsContract.CommonDataKinds.Phone.TYPE_WORK -> PhoneType.WORK
                    else -> PhoneType.OTHER
                }

                contacts.getOrPut(id) { mutableListOf() }
                    .add(PhoneNumber(number, phoneType))
            }
        }

        // Convert to Contact objects
        contacts.map { (id, phoneNumbers) ->
            val (name, photoUri) = contactsInfo[id] ?: ("Unknown" to null)
            Contact(
                id = id,
                name = name,
                phoneNumber = phoneNumbers.firstOrNull()?.number ?: "",
                phoneType = phoneNumbers.firstOrNull()?.type ?: PhoneType.OTHER,
                photoUri = photoUri
            )
        }.sortedBy { it.name }
    }

    fun searchContacts(contacts: List<Contact>, query: String): List<Contact> {
        if (query.isBlank()) return contacts
        val lowerQuery = query.lowercase()
        return contacts.filter { contact ->
            contact.name.lowercase().contains(lowerQuery) ||
                    contact.phoneNumber.contains(lowerQuery)
        }
    }
}
