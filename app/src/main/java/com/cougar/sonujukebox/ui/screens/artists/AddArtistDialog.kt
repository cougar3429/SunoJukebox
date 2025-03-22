package com.cougar.sonujukebox.ui.screens.artists

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddArtistDialog(
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