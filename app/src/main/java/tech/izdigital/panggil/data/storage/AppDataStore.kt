package tech.izdigital.panggil.data.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [PersonRecord::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataStore : RoomDatabase() {
    
    abstract fun personDataAccess(): PersonDataAccess
    
    companion object {
        @Volatile
        private var instance: AppDataStore? = null
        
        fun obtain(ctx: Context): AppDataStore {
            return instance ?: synchronized(this) {
                val built = Room.databaseBuilder(
                    ctx.applicationContext,
                    AppDataStore::class.java,
                    "panggil_data.db"
                ).build()
                instance = built
                built
            }
        }
    }
}
