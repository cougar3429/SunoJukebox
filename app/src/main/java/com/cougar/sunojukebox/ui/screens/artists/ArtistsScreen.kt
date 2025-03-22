package com.cougar.sunojukebox.ui.screens.artists

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
import com.cougar.sunojukebox.ui.viewmodels.ArtistsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistsScreen(
    onArtistClick: (String) -> Unit,
    artistsViewModel: ArtistsViewModel
) {
    val artists by artistsViewModel.artists.collectAsState(initial = emptyList())
    var showAddArtistDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddArtistDialog = true }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Artist")
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
                text = "Artists",
                style = MaterialTheme.typography.headlineMedium
            )
            
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(artists) { artist ->
                    Card(
                        onClick = { onArtistClick(artist.id) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = artist.name,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }

        if (showAddArtistDialog) {
            AddArtistDialog(
                onDismiss = { showAddArtistDialog = false },
                onConfirm = { name ->
                    artistsViewModel.addArtist(name)
                    showAddArtistDialog = false
                }
            )
        }
    }
}

@Composable
private fun AddArtistDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var artistName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Artist") },
        text = {
            OutlinedTextField(
                value = artistName,
                onValueChange = { artistName = it },
                label = { Text("Artist Name") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (artistName.isNotBlank()) {
                        onConfirm(artistName)
                        onDismiss()
                    }
                },
                enabled = artistName.isNotBlank()
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