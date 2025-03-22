package com.cougar.sunojukebox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cougar.sunojukebox.ui.screens.MainScreen
import com.cougar.sunojukebox.ui.theme.SunoJukeboxTheme
import com.cougar.sunojukebox.ui.viewmodels.ArtistsViewModel
import com.cougar.sunojukebox.ui.viewmodels.PlaylistsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SunoJukeboxTheme {
                val artistsViewModel: ArtistsViewModel = viewModel()
                val playlistsViewModel: PlaylistsViewModel = viewModel()
                
                MainScreen(
                    artistsViewModel = artistsViewModel,
                    playlistsViewModel = playlistsViewModel
                )
            }
        }
    }
}