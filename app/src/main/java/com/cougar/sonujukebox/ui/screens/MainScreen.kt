package com.cougar.sonujukebox.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cougar.sonujukebox.R
import com.cougar.sonujukebox.ui.navigation.Screen
import com.cougar.sonujukebox.ui.screens.artists.ArtistDetailScreen
import com.cougar.sonujukebox.ui.screens.artists.ArtistsScreen
import com.cougar.sonujukebox.ui.screens.playlists.PlaylistDetailScreen
import com.cougar.sonujukebox.ui.screens.playlists.PlaylistsScreen
import com.cougar.sonujukebox.ui.viewmodels.ArtistsViewModel
import com.cougar.sonujukebox.ui.viewmodels.ArtistsViewModelFactory
import com.cougar.sonujukebox.ui.viewmodels.PlaylistsViewModel
import com.cougar.sonujukebox.ui.viewmodels.PlaylistsViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    artistsViewModelFactory: ArtistsViewModelFactory,
    playlistsViewModelFactory: PlaylistsViewModelFactory
) {
    val navController = rememberNavController()
    val artistsViewModel: ArtistsViewModel = viewModel(factory = artistsViewModelFactory)
    val playlistsViewModel: PlaylistsViewModel = viewModel(factory = playlistsViewModelFactory)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                val items = listOf(
                    Screen.Artists to Icons.Default.List,
                    Screen.Playlists to Icons.Default.PlaylistAdd
                )

                items.forEach { (screen, icon) ->
                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
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
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Artists.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Artists.route) {
                ArtistsScreen(
                    viewModel = artistsViewModel,
                    onArtistClick = { artistId ->
                        navController.navigate("artist/$artistId")
                    }
                )
            }
            composable(
                route = Screen.ArtistDetail.route,
                arguments = Screen.ArtistDetail.arguments
            ) {
                ArtistDetailScreen(
                    viewModel = artistsViewModel,
                    onNavigateBack = { navController.navigateUp() }
                )
            }
            composable(Screen.Playlists.route) {
                PlaylistsScreen(
                    viewModel = playlistsViewModel,
                    onPlaylistClick = { playlistId ->
                        navController.navigate("playlist/$playlistId")
                    }
                )
            }
            composable(
                route = Screen.PlaylistDetail.route,
                arguments = Screen.PlaylistDetail.arguments
            ) {
                PlaylistDetailScreen(
                    viewModel = playlistsViewModel,
                    onNavigateBack = { navController.navigateUp() }
                )
            }
        }
    }
} 