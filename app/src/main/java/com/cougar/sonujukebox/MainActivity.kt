package com.cougar.sonujukebox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cougar.sonujukebox.di.AppModule
import com.cougar.sonujukebox.ui.screens.MainScreen
import com.cougar.sonujukebox.ui.theme.SunoJukeboxTheme
import com.cougar.sonujukebox.ui.viewmodels.ArtistsViewModel
import com.cougar.sonujukebox.ui.viewmodels.ArtistsViewModelFactory
import com.cougar.sonujukebox.ui.viewmodels.PlaylistsViewModel
import com.cougar.sonujukebox.ui.viewmodels.PlaylistsViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val repository = AppModule.provideRepository(this)
        val artistsViewModelFactory = ArtistsViewModelFactory(repository)
        val playlistsViewModelFactory = PlaylistsViewModelFactory(repository)
        
        setContent {
            SunoJukeboxTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        artistsViewModelFactory = artistsViewModelFactory,
                        playlistsViewModelFactory = playlistsViewModelFactory
                    )
                }
            }
        }
    }
}