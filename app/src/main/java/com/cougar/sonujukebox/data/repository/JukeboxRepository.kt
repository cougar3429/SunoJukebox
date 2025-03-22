package com.cougar.sonujukebox.data.repository

import com.cougar.sonujukebox.data.models.Artist
import com.cougar.sonujukebox.data.models.Playlist
import com.cougar.sonujukebox.data.models.Track
import kotlinx.coroutines.flow.Flow

interface JukeboxRepository {
    // Artist operations
    fun getAllArtists(): Flow<List<Artist>>
    fun getArtist(artistId: String): Flow<Artist?>
    suspend fun createArtist(name: String)
    suspend fun deleteArtist(artistId: String)
    
    // Track operations
    suspend fun addTrack(artistId: String, title: String)
    suspend fun deleteTrack(trackId: String)
    fun getTracksByArtist(artistId: String): Flow<List<Track>>
    
    // Playlist operations
    fun getAllPlaylists(): Flow<List<Playlist>>
    fun getPlaylist(playlistId: String): Flow<Playlist?>
    suspend fun createPlaylist(name: String)
    suspend fun deletePlaylist(playlistId: String)
    suspend fun addTrackToPlaylist(playlistId: String, trackId: String)
    suspend fun removeTrackFromPlaylist(playlistId: String, trackId: String)
} 