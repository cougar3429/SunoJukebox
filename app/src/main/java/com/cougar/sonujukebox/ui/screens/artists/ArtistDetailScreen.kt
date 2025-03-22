package com.cougar.sonujukebox.ui.screens.artists

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
import com.cougar.sonujukebox.ui.viewmodels.ArtistsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistDetailScreen(
    viewModel: ArtistsViewModel,
    onNavigateBack: () -> Unit
) {
    val artist by viewModel.currentArtist.collectAsState()
    var showAddTrackDialog by remember { mutableStateOf(false) }
    var newTrackTitle by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(artist?.name ?: "Artist Details") },
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
                text = "Number of tracks: ${artist?.tracks?.size ?: 0}",
                style = MaterialTheme.typography.titleLarge
            )
            
            // TODO: Add track list here
        }

        if (showAddTrackDialog) {
            AlertDialog(
                onDismissRequest = { showAddTrackDialog = false },
                title = { Text("Add New Track") },
                text = {
                    OutlinedTextField(
                        value = newTrackTitle,
                        onValueChange = { newTrackTitle = it },
                        label = { Text("Track Title") }
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if (newTrackTitle.isNotBlank() && artist != null) {
                                viewModel.addTrack(artist!!.id, newTrackTitle)
                                newTrackTitle = ""
                                showAddTrackDialog = false
                            }
                        }
                    ) {
                        Text("Add")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAddTrackDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
private fun AddTrackDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var trackTitle by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Track") },
        text = {
            OutlinedTextField(
                value = trackTitle,
                onValueChange = { trackTitle = it },
                label = { Text("Track Title") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (trackTitle.isNotBlank()) {
                        onConfirm(trackTitle)
                        onDismiss()
                    }
                },
                enabled = trackTitle.isNotBlank()
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