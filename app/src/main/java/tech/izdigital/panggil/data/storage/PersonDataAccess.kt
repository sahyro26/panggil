package tech.izdigital.panggil.data.storage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDataAccess {
    
    @Query("SELECT * FROM people_records ORDER BY person_name ASC")
    fun streamAllPeople(): Flow<List<PersonRecord>>
    
    @Query("SELECT * FROM people_records WHERE record_id = :recordId")
    suspend fun fetchPerson(recordId: Long): PersonRecord?
    
    @Insert
    suspend fun storePerson(person: PersonRecord): Long
    
    @Update
    suspend fun changePerson(person: PersonRecord)
    
    @Delete
    suspend fun erasePerson(person: PersonRecord)
    
    @Query("DELETE FROM people_records WHERE record_id = :recordId")
    suspend fun erasePersonById(recordId: Long)
    
    @Query("SELECT COUNT(*) FROM people_records")
    suspend fun countPeople(): Int
}
