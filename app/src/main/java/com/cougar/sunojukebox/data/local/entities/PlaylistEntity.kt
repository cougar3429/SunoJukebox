package com.cougar.sunojukebox.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cougar.sunojukebox.data.models.Playlist

@Entity(tableName = "playlists")
data class PlaylistEntity(
    @PrimaryKey
    val id: String,
    val name: String
) {
    fun toPlaylist(tracks: List<TrackEntity>): Playlist {
        return Playlist(
            id = id,
            name = name,
            tracks = tracks.map { it.toTrack() }
        )
    }
    
    companion object {
        fun fromPlaylist(playlist: Playlist): PlaylistEntity {
            return PlaylistEntity(
                id = playlist.id,
                name = playlist.name
            )
        }
    }
} 