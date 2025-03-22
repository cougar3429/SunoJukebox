package com.cougar.sunojukebox.data.repository

import com.cougar.sunojukebox.data.models.Artist
import com.cougar.sunojukebox.data.models.Playlist
import com.cougar.sunojukebox.data.models.Track
import kotlinx.coroutines.flow.Flow

interface JukeboxRepository {
    // Artist operations
    fun getAllArtists(): Flow<List<Artist>>
    fun getArtistById(id: String): Flow<Artist?>
    suspend fun addArtist(name: String)
    suspend fun deleteArtist(id: String)
    
    // Track operations
    suspend fun addTrack(track: Track)
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