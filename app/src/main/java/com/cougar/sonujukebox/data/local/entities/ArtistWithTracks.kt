package com.cougar.sonujukebox.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ArtistWithTracks(
    @Embedded val artist: ArtistEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "artistId"
    )
    val tracks: List<TrackEntity>
) 