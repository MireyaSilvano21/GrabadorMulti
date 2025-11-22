package mx.edu.utez.grabador.data.dao

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow
import mx.edu.utez.grabador.data.model.MediaItem
import mx.edu.utez.grabador.data.model.MediaType

// --- 2. DAO (Data Access Object) ---
@Dao
interface MediaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedia(item: MediaItem)
    @Query("SELECT * FROM media_items WHERE type = :type ORDER BY date DESC")
    fun getMediaByType(type: MediaType): Flow<List<MediaItem>>
}


// --- 3. Database (inicialización y control de BD)---
@Database(entities = [MediaItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mediaDao(): MediaDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "media_app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}