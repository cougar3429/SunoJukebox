package com.cougar.sonujukebox.ui.screens.artists

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
import com.cougar.sonujukebox.ui.viewmodels.ArtistsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistsScreen(
    viewModel: ArtistsViewModel,
    onArtistClick: (String) -> Unit
) {
    val artists by viewModel.artists.collectAsState()
    var showAddArtistDialog by remember { mutableStateOf(false) }
    var newArtistName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Artists") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddArtistDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Artist")
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
            items(artists) { artist ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onArtistClick(artist.id) }
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
                                text = artist.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Tracks: ${artist.tracks.size}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        IconButton(
                            onClick = { /* TODO: Show artist options menu */ }
                        ) {
                            Text("⋮")
                        }
                    }
                }
            }
        }

        if (showAddArtistDialog) {
            AlertDialog(
                onDismissRequest = { showAddArtistDialog = false },
                title = { Text("Add New Artist") },
                text = {
                    OutlinedTextField(
                        value = newArtistName,
                        onValueChange = { newArtistName = it },
                        label = { Text("Artist Name") }
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if (newArtistName.isNotBlank()) {
                                viewModel.createArtist(newArtistName)
                                newArtistName = ""
                                showAddArtistDialog = false
                            }
                        }
                    ) {
                        Text("Add")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAddArtistDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
} 