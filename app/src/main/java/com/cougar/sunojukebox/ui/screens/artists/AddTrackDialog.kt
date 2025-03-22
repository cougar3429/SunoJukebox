package com.cougar.sunojukebox.ui.screens.artists

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cougar.sunojukebox.data.models.Track
import java.util.*

@Composable
fun AddTrackDialog(
    artistId: String,
    onDismiss: () -> Unit,
    onAddTrack: (Track) -> Unit
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
                        val track = Track(
                            id = UUID.randomUUID().toString(),
                            title = trackTitle,
                            artistId = artistId,
                            duration = 0,
                            date = Date(),
                            url = null,
                            comments = null,
                            genre = null,
                            category = null
                        )
                        onAddTrack(track)
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