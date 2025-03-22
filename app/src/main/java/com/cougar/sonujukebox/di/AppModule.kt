package com.cougar.sonujukebox.di

import android.content.Context
import com.cougar.sonujukebox.data.local.JukeboxDatabase
import com.cougar.sonujukebox.data.repository.JukeboxRepository
import com.cougar.sonujukebox.data.repository.JukeboxRepositoryImpl

object AppModule {
    private var repository: JukeboxRepository? = null
    
    fun provideRepository(context: Context): JukeboxRepository {
        return repository ?: synchronized(this) {
            val database = JukeboxDatabase.getDatabase(context)
            val instance = JukeboxRepositoryImpl(
                artistDao = database.artistDao(),
                trackDao = database.trackDao(),
                playlistDao = database.playlistDao()
            )
            repository = instance
            instance
        }
    }
} 