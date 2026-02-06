package tech.izdigital.panggil.domain

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tech.izdigital.panggil.data.model.Contact
import tech.izdigital.panggil.data.storage.AppDataStore
import tech.izdigital.panggil.data.storage.PersonDataAccess
import tech.izdigital.panggil.data.storage.PersonRecord

class PersonManager(ctx: Context) {
    private val accessor: PersonDataAccess = AppDataStore.obtain(ctx).personDataAccess()
    
    fun observePeople(): Flow<List<Contact>> {
        return accessor.streamAllPeople().map { records ->
            records.map { rec -> rec.asContact() }
        }
    }
    
    suspend fun registerPerson(
        name: String,
        phone: String,
        email: String? = null
    ): Outcome<Long> {
        return try {
            val rec = PersonRecord(
                personName = name,
                phoneDigits = phone,
                mailAddress = email,
                addedManually = true
            )
            val id = accessor.storePerson(rec)
            Outcome.Success(id)
        } catch (ex: Exception) {
            Outcome.Failure(ex.message ?: "Unknown error")
        }
    }
    
    suspend fun modifyPerson(
        id: Long,
        name: String,
        phone: String,
        email: String? = null
    ): Outcome<Unit> {
        return try {
            val existing = accessor.fetchPerson(id)
            if (existing != null) {
                val modified = existing.copy(
                    personName = name,
                    phoneDigits = phone,
                    mailAddress = email
                )
                accessor.changePerson(modified)
                Outcome.Success(Unit)
            } else {
                Outcome.Failure("Person not found")
            }
        } catch (ex: Exception) {
            Outcome.Failure(ex.message ?: "Unknown error")
        }
    }
    
    suspend fun removePerson(id: Long): Outcome<Unit> {
        return try {
            accessor.erasePersonById(id)
            Outcome.Success(Unit)
        } catch (ex: Exception) {
            Outcome.Failure(ex.message ?: "Unknown error")
        }
    }
    
    suspend fun countManuallyAdded(): Int {
        return accessor.countPeople()
    }
}

private fun PersonRecord.asContact(): Contact {
    return Contact(
        id = "manual_$recordId",
        name = personName,
        phoneNumber = phoneDigits,
        email = mailAddress,
        photoUri = picturePath,
        isFavorite = false
    )
}

sealed class Outcome<out T> {
    data class Success<T>(val data: T) : Outcome<T>()
    data class Failure(val reason: String) : Outcome<Nothing>()
    
    val succeeded: Boolean get() = this is Success
    val failed: Boolean get() = this is Failure
}
