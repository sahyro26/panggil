// app/src/main/java/tech/izdigital/panggil/data/model/Contact.kt
package tech.izdigital.panggil.data.model

data class Contact(
    val id: String,
    val name: String,
    val phoneNumbers: List<PhoneNumber>,
    val photoUri: String? = null,
    val emails: List<String> = emptyList()
)

data class PhoneNumber(
    val number: String,
    val type: PhoneType
)

enum class PhoneType {
    MOBILE, HOME, WORK, OTHER
}
