package com.cougar.sunojukebox.ui.screens.playlists

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
import com.cougar.sunojukebox.ui.viewmodels.PlaylistsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistDetailScreen(
    playlistId: String,
    onNavigateBack: () -> Unit,
    playlistsViewModel: PlaylistsViewModel
) {
    val playlist by playlistsViewModel.getPlaylist(playlistId).collectAsState(initial = null)
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
            FloatingActionButton(
                onClick = { showAddTrackDialog = true }
            ) {
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
            
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(playlist?.tracks ?: emptyList()) { track ->
                    Text(
                        text = track.title,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }

        if (showAddTrackDialog && playlist != null) {
            AddTrackToPlaylistDialog(
                playlistId = playlistId,
                onDismiss = { showAddTrackDialog = false },
                onAddTrack = { trackId ->
                    playlistsViewModel.addTrackToPlaylist(playlistId, trackId)
                    showAddTrackDialog = false
                }
            )
        }
    }
}

@Composable
private fun AddTrackToPlaylistDialog(
    playlistId: String,
    onDismiss: () -> Unit,
    onAddTrack: (String) -> Unit
) {
    var trackId by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Track to Playlist") },
        text = {
            OutlinedTextField(
                value = trackId,
                onValueChange = { trackId = it },
                label = { Text("Track ID") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (trackId.isNotBlank()) {
                        onAddTrack(trackId)
                        onDismiss()
                    }
                },
                enabled = trackId.isNotBlank()
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
} 