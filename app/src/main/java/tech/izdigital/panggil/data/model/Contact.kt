// app/src/main/java/tech/izdigital/panggil/data/model/Contact.kt
package tech.izdigital.panggil.data.model

data class Contact(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val phoneType: PhoneType,
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
