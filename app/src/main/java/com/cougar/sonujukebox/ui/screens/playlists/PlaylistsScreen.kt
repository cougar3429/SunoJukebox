package com.cougar.sonujukebox.ui.screens.playlists

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cougar.sonujukebox.ui.navigation.Screen
import com.cougar.sonujukebox.ui.viewmodels.PlaylistsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistsScreen(
    viewModel: PlaylistsViewModel,
    onPlaylistClick: (String) -> Unit
) {
    val playlists by viewModel.playlists.collectAsState()
    var showAddPlaylistDialog by remember { mutableStateOf(false) }
    var newPlaylistName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Playlists") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddPlaylistDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Playlist")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(playlists) { playlist ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onPlaylistClick(playlist.id) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = playlist.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Tracks: ${playlist.tracks.size}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        IconButton(
                            onClick = { /* TODO: Show playlist options menu */ }
                        ) {
                            Text("⋮")
                        }
                    }
                }
            }
        }

        if (showAddPlaylistDialog) {
            AlertDialog(
                onDismissRequest = { showAddPlaylistDialog = false },
                title = { Text("Add New Playlist") },
                text = {
                    OutlinedTextField(
                        value = newPlaylistName,
                        onValueChange = { newPlaylistName = it },
                        label = { Text("Playlist Name") }
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if (newPlaylistName.isNotBlank()) {
                                viewModel.createPlaylist(newPlaylistName)
                                newPlaylistName = ""
                                showAddPlaylistDialog = false
                            }
                        }
                    ) {
                        Text("Add")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAddPlaylistDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
} 