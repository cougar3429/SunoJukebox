package com.cougar.sunojukebox.data.local.dao

import androidx.room.*
import com.cougar.sunojukebox.data.local.entities.ArtistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtistDao {
    @Query("SELECT * FROM artists")
    fun getAllArtists(): Flow<List<ArtistEntity>>

    @Query("SELECT * FROM artists WHERE id = :id")
    fun getArtistById(id: String): Flow<ArtistEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtist(artist: ArtistEntity)

    @Delete
    suspend fun deleteArtist(artist: ArtistEntity)

    @Query("DELETE FROM artists WHERE id = :id")
    suspend fun deleteArtist(id: String)
} 