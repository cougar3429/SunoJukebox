package com.cougar.sunojukebox.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cougar.sunojukebox.data.models.Track
import java.util.Date

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val artistId: String,
    val artistName: String,
    val name: String,
    val duration: Long?,
    val date: Date?,
    val url: String?,
    val comments: String?,
    val genre: String?,
    val category: String?
) {
    companion object {
        fun fromTrack(track: Track, artistId: String): TrackEntity {
            return TrackEntity(
                id = track.id,
                title = track.title,
                artistId = artistId,
                artistName = track.artistName,
                name = track.name,
                duration = track.duration,
                date = track.date,
                url = track.url,
                comments = track.comments,
                genre = track.genre,
                category = track.category
            )
        }
    }

    fun toTrack(): Track {
        return Track(
            id = id,
            title = title,
            artistId = artistId,
            artistName = artistName,
            name = name,
            duration = duration,
            date = date,
            url = url,
            comments = comments,
            genre = genre,
            category = category
        )
    }
} 