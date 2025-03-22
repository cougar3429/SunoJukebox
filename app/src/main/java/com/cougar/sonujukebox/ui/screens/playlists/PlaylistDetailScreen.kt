package com.cougar.sonujukebox.ui.screens.playlists

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cougar.sonujukebox.ui.viewmodels.PlaylistsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistDetailScreen(
    viewModel: PlaylistsViewModel,
    onNavigateBack: () -> Unit
) {
    val playlist by viewModel.currentPlaylist.collectAsState()
    var showAddTrackDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(playlist?.name ?: "Playlist Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddTrackDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Track")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Number of tracks: ${playlist?.tracks?.size ?: 0}",
                style = MaterialTheme.typography.titleLarge
            )
            
            // TODO: Add track list here
        }

        if (showAddTrackDialog) {
            AlertDialog(
                onDismissRequest = { showAddTrackDialog = false },
                title = { Text("Add Track to Playlist") },
                text = {
                    // TODO: Add track selection UI
                    Text("Track selection coming soon...")
                },
                confirmButton = {
                    TextButton(
                        onClick = { showAddTrackDialog = false }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
} 