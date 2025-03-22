package com.cougar.sunojukebox.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlaylistPlay
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cougar.sunojukebox.ui.navigation.Screen
import com.cougar.sunojukebox.ui.screens.artists.ArtistsScreen
import com.cougar.sunojukebox.ui.screens.artists.ArtistDetailScreen
import com.cougar.sunojukebox.ui.screens.playlists.PlaylistsScreen
import com.cougar.sunojukebox.ui.screens.playlists.PlaylistDetailScreen
import com.cougar.sunojukebox.ui.viewmodels.ArtistsViewModel
import com.cougar.sunojukebox.ui.viewmodels.PlaylistsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    artistsViewModel: ArtistsViewModel,
    playlistsViewModel: PlaylistsViewModel
) {
    val navController = rememberNavController()
    var selectedItem by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                val items = listOf(
                    Screen.Artists to Icons.Default.List,
                    Screen.Playlists to Icons.Default.PlaylistPlay
                )
                items.forEachIndexed { index, (screen, icon) ->
                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = screen.route) },
                        label = { Text(screen.route) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Artists.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Artists.route) {
                ArtistsScreen(
                    onArtistClick = { artistId ->
                        navController.navigate(Screen.ArtistDetail.createRoute(artistId))
                    },
                    artistsViewModel = artistsViewModel
                )
            }
            composable(
                route = Screen.ArtistDetail.route,
                arguments = Screen.ArtistDetail.arguments
            ) { backStackEntry ->
                val artistId = backStackEntry.arguments?.getString("artistId") ?: return@composable
                ArtistDetailScreen(
                    artistId = artistId,
                    onNavigateBack = { navController.popBackStack() },
                    artistsViewModel = artistsViewModel
                )
            }
            composable(Screen.Playlists.route) {
                PlaylistsScreen(
                    onPlaylistClick = { playlistId ->
                        navController.navigate(Screen.PlaylistDetail.createRoute(playlistId))
                    },
                    playlistsViewModel = playlistsViewModel
                )
            }
            composable(
                route = Screen.PlaylistDetail.route,
                arguments = Screen.PlaylistDetail.arguments
            ) { backStackEntry ->
                val playlistId = backStackEntry.arguments?.getString("playlistId") ?: return@composable
                PlaylistDetailScreen(
                    playlistId = playlistId,
                    onNavigateBack = { navController.popBackStack() },
                    playlistsViewModel = playlistsViewModel
                )
            }
        }
    }
} 