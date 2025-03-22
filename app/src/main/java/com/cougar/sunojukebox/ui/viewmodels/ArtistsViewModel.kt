package com.cougar.sunojukebox.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cougar.sunojukebox.data.models.Artist
import com.cougar.sunojukebox.data.models.Track
import com.cougar.sunojukebox.data.repository.JukeboxRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ArtistsViewModel(
    private val repository: JukeboxRepository
) : ViewModel() {
    val artists: Flow<List<Artist>> = repository.getAllArtists()

    fun getArtist(id: String): Flow<Artist?> = repository.getArtistById(id)

    fun addArtist(name: String) {
        viewModelScope.launch {
            repository.addArtist(name)
        }
    }

    fun addTrack(track: Track) {
        viewModelScope.launch {
            repository.addTrack(track)
        }
    }

    fun deleteArtist(id: String) {
        viewModelScope.launch {
            repository.deleteArtist(id)
        }
    }
}

class ArtistsViewModelFactory(
    private val repository: JukeboxRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArtistsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ArtistsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 