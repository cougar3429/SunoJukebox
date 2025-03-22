package com.cougar.sunojukebox.ui.screens.artists

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
import com.cougar.sunojukebox.ui.viewmodels.ArtistsViewModel
import com.cougar.sunojukebox.data.models.Track
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistDetailScreen(
    artistId: String,
    onNavigateBack: () -> Unit,
    artistsViewModel: ArtistsViewModel
) {
    val artist by artistsViewModel.getArtist(artistId).collectAsState(initial = null)
    var showAddTrackDialog by remember { mutableStateOf(false) }

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
                text = "Number of tracks: ${artist?.tracks?.size ?: 0}",
                style = MaterialTheme.typography.titleLarge
            )
            
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(artist?.tracks ?: emptyList()) { track ->
                    Text(
                        text = track.title,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }

        if (showAddTrackDialog) {
            AddTrackDialog(
                onDismiss = { showAddTrackDialog = false },
                onConfirm = { title ->
                    artistsViewModel.addTrack(
                        Track(
                            id = UUID.randomUUID().toString(),
                            title = title,
                            artistId = artistId,
                            artistName = artist?.name ?: "",
                            name = title
                        )
                    )
                    showAddTrackDialog = false
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