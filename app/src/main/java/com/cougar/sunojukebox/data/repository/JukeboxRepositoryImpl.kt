package com.cougar.sunojukebox.data.repository

import com.cougar.sunojukebox.data.local.dao.ArtistDao
import com.cougar.sunojukebox.data.local.dao.PlaylistDao
import com.cougar.sunojukebox.data.local.dao.TrackDao
import com.cougar.sunojukebox.data.local.entities.ArtistEntity
import com.cougar.sunojukebox.data.local.entities.PlaylistEntity
import com.cougar.sunojukebox.data.local.entities.TrackEntity
import com.cougar.sunojukebox.data.local.entities.PlaylistTrackCrossRef
import com.cougar.sunojukebox.data.local.entities.PlaylistWithTracks
import com.cougar.sunojukebox.data.models.Artist
import com.cougar.sunojukebox.data.models.Playlist
import com.cougar.sunojukebox.data.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.util.*

class JukeboxRepositoryImpl(
    private val artistDao: ArtistDao,
    private val trackDao: TrackDao,
    private val playlistDao: PlaylistDao
) : JukeboxRepository {

    override fun getAllArtists(): Flow<List<Artist>> {
        return combine(
            artistDao.getAllArtists(),
            trackDao.getAllTracks()
        ) { artists: List<ArtistEntity>, tracks: List<TrackEntity> ->
            artists.map { artist ->
                artist.toArtist(tracks.filter { it.artistId == artist.id })
            }
        }
    }

    override fun getArtistById(id: String): Flow<Artist?> {
        return combine(
            artistDao.getArtistById(id),
            trackDao.getTracksByArtist(id)
        ) { artist: ArtistEntity?, tracks: List<TrackEntity> ->
            artist?.toArtist(tracks)
        }
    }

    override suspend fun addArtist(name: String) {
        val artist = Artist(
            id = UUID.randomUUID().toString(),
            name = name,
            tracks = emptyList()
        )
        artistDao.insertArtist(ArtistEntity.fromArtist(artist))
    }

    override suspend fun addTrack(track: Track) {
        trackDao.insertTrack(TrackEntity.fromTrack(track, track.artistId))
    }

    override suspend fun deleteArtist(id: String) {
        artistDao.deleteArtist(id)
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return playlistDao.getAllPlaylistsWithTracks().map { playlistWithTracksList ->
            playlistWithTracksList.map { playlistWithTracks ->
                playlistWithTracks.playlist.toPlaylist(playlistWithTracks.tracks)
            }
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
        playlistDao.deletePlaylist(playlistId)
    }

    override suspend fun deleteTrack(trackId: String) {
        trackDao.deleteTrack(trackId)
    }

    override fun getTracksByArtist(artistId: String): Flow<List<Track>> {
        return trackDao.getTracksByArtist(artistId).map { tracks ->
            tracks.map { it.toTrack() }
        }
    }
} 