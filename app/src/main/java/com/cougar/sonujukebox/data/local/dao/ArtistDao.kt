package com.cougar.sonujukebox.data.local.dao

import androidx.room.*
import com.cougar.sonujukebox.data.local.entities.ArtistEntity
import com.cougar.sonujukebox.data.local.entities.ArtistWithTracks
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtistDao {
    @Query("SELECT * FROM artists")
    fun getAllArtistsWithTracks(): Flow<List<ArtistWithTracks>>

    @Query("SELECT * FROM artists WHERE id = :artistId")
    suspend fun getArtistById(artistId: String): ArtistEntity?

    @Query("SELECT * FROM artists WHERE id = :artistId")
    fun getArtistWithTracks(artistId: String): Flow<ArtistWithTracks?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtist(artist: ArtistEntity)

    @Delete
    suspend fun deleteArtist(artist: ArtistEntity)
} 