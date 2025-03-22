package com.cougar.sonujukebox.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cougar.sonujukebox.data.models.Artist
import com.cougar.sonujukebox.data.models.Track
import com.cougar.sonujukebox.data.repository.JukeboxRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ArtistsViewModel(
    private val repository: JukeboxRepository
) : ViewModel() {
    private val _artists = MutableStateFlow<List<Artist>>(emptyList())
    val artists: StateFlow<List<Artist>> = _artists.asStateFlow()

    private val _currentArtist = MutableStateFlow<Artist?>(null)
    val currentArtist: StateFlow<Artist?> = _currentArtist.asStateFlow()

    init {
        loadArtists()
    }

    private fun loadArtists() {
        viewModelScope.launch {
            repository.getAllArtists().collect { artists ->
                _artists.value = artists
            }
        }
    }

    fun getArtist(artistId: String): StateFlow<Artist?> {
        viewModelScope.launch {
            repository.getArtist(artistId).collect { artist ->
                _currentArtist.value = artist
            }
        }
        return currentArtist
    }

    fun createArtist(name: String) {
        viewModelScope.launch {
            repository.createArtist(name)
        }
    }

    fun addTrack(artistId: String, title: String) {
        viewModelScope.launch {
            repository.addTrack(artistId, title)
        }
    }

    fun deleteArtist(artistId: String) {
        viewModelScope.launch {
            repository.deleteArtist(artistId)
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