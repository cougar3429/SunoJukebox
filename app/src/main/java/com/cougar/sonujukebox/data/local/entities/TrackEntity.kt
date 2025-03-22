package com.cougar.sonujukebox.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.cougar.sonujukebox.data.local.converters.DateConverter
import com.cougar.sonujukebox.data.models.Track
import java.util.Date

@Entity(
    tableName = "tracks",
    foreignKeys = [
        ForeignKey(
            entity = ArtistEntity::class,
            parentColumns = ["id"],
            childColumns = ["artistId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("artistId")
    ]
)
@TypeConverters(DateConverter::class)
data class TrackEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val artistId: String,
    val artistName: String,
    val name: String,
    val url: String?,
    val date: Date?,
    val comments: String?,
    val genre: String?,
    val category: String?,
    val duration: Long?
) {
    fun toTrack(): Track {
        return Track(
            id = id,
            title = title,
            artistId = artistId,
            artistName = artistName,
            name = name,
            url = url,
            date = date,
            comments = comments,
            genre = genre,
            category = category,
            duration = duration
        )
    }
    
    companion object {
        fun fromTrack(track: Track, artistId: String): TrackEntity {
            return TrackEntity(
                id = track.id,
                title = track.title,
                artistId = artistId,
                artistName = track.artistName,
                name = track.name,
                url = track.url,
                date = track.date,
                comments = track.comments,
                genre = track.genre,
                category = track.category,
                duration = track.duration
            )
        }
    }
} 