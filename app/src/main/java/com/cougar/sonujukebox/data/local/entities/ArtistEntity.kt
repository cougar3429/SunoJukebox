package com.cougar.sonujukebox.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cougar.sonujukebox.data.models.Artist

@Entity(tableName = "artists")
data class ArtistEntity(
    @PrimaryKey
    val id: String,
    val name: String
) {
    fun toArtist(tracks: List<TrackEntity>): Artist {
        return Artist(
            id = id,
            name = name,
            tracks = tracks.map { it.toTrack() }
        )
    }
    
    companion object {
        fun fromArtist(artist: Artist): ArtistEntity {
            return ArtistEntity(
                id = artist.id,
                name = artist.name
            )
        }
    }
} 