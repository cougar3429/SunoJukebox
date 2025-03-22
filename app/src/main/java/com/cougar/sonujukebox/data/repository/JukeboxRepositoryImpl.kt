package com.cougar.sonujukebox.data.repository

import com.cougar.sonujukebox.data.local.dao.ArtistDao
import com.cougar.sonujukebox.data.local.dao.PlaylistDao
import com.cougar.sonujukebox.data.local.dao.TrackDao
import com.cougar.sonujukebox.data.local.entities.ArtistEntity
import com.cougar.sonujukebox.data.local.entities.PlaylistEntity
import com.cougar.sonujukebox.data.local.entities.TrackEntity
import com.cougar.sonujukebox.data.local.entities.PlaylistTrackCrossRef
import com.cougar.sonujukebox.data.models.Artist
import com.cougar.sonujukebox.data.models.Playlist
import com.cougar.sonujukebox.data.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class JukeboxRepositoryImpl(
    private val artistDao: ArtistDao,
    private val trackDao: TrackDao,
    private val playlistDao: PlaylistDao
) : JukeboxRepository {

    override fun getAllArtists(): Flow<List<Artist>> {
        return artistDao.getAllArtistsWithTracks().map { artistWithTracksList ->
            artistWithTracksList.map { it.artist.toArtist(it.tracks) }
        }
    }

    override fun getArtist(artistId: String): Flow<Artist?> {
        return artistDao.getArtistWithTracks(artistId).map { artistWithTracks ->
            artistWithTracks?.let { it.artist.toArtist(it.tracks) }
        }
    }

    override suspend fun createArtist(name: String) {
        val id = UUID.randomUUID().toString()
        artistDao.insertArtist(ArtistEntity(id = id, name = name))
    }

    override suspend fun addTrack(artistId: String, title: String) {
        val artist = artistDao.getArtistById(artistId) ?: return
        val trackId = UUID.randomUUID().toString()
        val track = TrackEntity(
            id = trackId,
            title = title,
            artistId = artistId,
            artistName = artist.name,
            name = title,
            url = null,
            date = Date(),
            comments = null,
            genre = null,
            category = null,
            duration = null
        )
        trackDao.insertTrack(track)
    }

    override suspend fun deleteArtist(artistId: String) {
        artistDao.getArtistById(artistId)?.let { artistDao.deleteArtist(it) }
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return playlistDao.getAllPlaylistsWithTracks().map { playlistWithTracksList ->
            playlistWithTracksList.map { it.playlist.toPlaylist(it.tracks) }
        }
    }

    override fun getPlaylist(playlistId: String): Flow<Playlist?> {
        return playlistDao.getPlaylistWithTracks(playlistId).map { playlistWithTracks ->
            playlistWithTracks?.let { it.playlist.toPlaylist(it.tracks) }
        }
    }

    override suspend fun createPlaylist(name: String) {
        val id = UUID.randomUUID().toString()
        playlistDao.insertPlaylist(PlaylistEntity(id = id, name = name))
    }

    override suspend fun addTrackToPlaylist(playlistId: String, trackId: String) {
        playlistDao.insertPlaylistTrackCrossRef(PlaylistTrackCrossRef(playlistId, trackId))
    }

    override suspend fun removeTrackFromPlaylist(playlistId: String, trackId: String) {
        playlistDao.removeTrackFromPlaylist(playlistId, trackId)
    }

    override suspend fun deletePlaylist(playlistId: String) {
        playlistDao.getPlaylistById(playlistId)?.let { playlistDao.deletePlaylist(it) }
    }

    override suspend fun deleteTrack(trackId: String) {
        trackDao.getTrackById(trackId)?.let { trackDao.deleteTrack(it) }
    }

    override fun getTracksByArtist(artistId: String): Flow<List<Track>> {
        return trackDao.getTracksByArtistId(artistId).map { tracks ->
            tracks.map { it.toTrack() }
        }
    }
} 