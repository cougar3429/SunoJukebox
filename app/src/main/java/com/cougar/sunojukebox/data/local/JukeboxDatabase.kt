package com.cougar.sunojukebox.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cougar.sunojukebox.data.local.dao.ArtistDao
import com.cougar.sunojukebox.data.local.dao.PlaylistDao
import com.cougar.sunojukebox.data.local.dao.TrackDao
import com.cougar.sunojukebox.data.local.entities.ArtistEntity
import com.cougar.sunojukebox.data.local.entities.PlaylistEntity
import com.cougar.sunojukebox.data.local.entities.TrackEntity
import com.cougar.sunojukebox.data.local.entities.PlaylistTrackCrossRef

@Database(
    entities = [
        ArtistEntity::class,
        TrackEntity::class,
        PlaylistEntity::class,
        PlaylistTrackCrossRef::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class JukeboxDatabase : RoomDatabase() {
    abstract fun artistDao(): ArtistDao
    abstract fun trackDao(): TrackDao
    abstract fun playlistDao(): PlaylistDao

    companion object {
        @Volatile
        private var INSTANCE: JukeboxDatabase? = null

        fun getDatabase(context: Context): JukeboxDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JukeboxDatabase::class.java,
                    "jukebox_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

@androidx.room.TypeConverters
class Converters {
    @androidx.room.TypeConverter
    fun fromTimestamp(value: Long?): java.util.Date? {
        return value?.let { java.util.Date(it) }
    }

    @androidx.room.TypeConverter
    fun dateToTimestamp(date: java.util.Date?): Long? {
        return date?.time
    }
} 