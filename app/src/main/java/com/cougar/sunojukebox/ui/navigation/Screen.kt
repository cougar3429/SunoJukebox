package com.cougar.sunojukebox.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String) {
    object Artists : Screen("artists")
    object Playlists : Screen("playlists")
    
    object ArtistDetail : Screen("artist/{artistId}") {
        val arguments = listOf(
            navArgument("artistId") { type = NavType.StringType }
        )
        
        fun createRoute(artistId: String) = "artist/$artistId"
    }
    
    object PlaylistDetail : Screen("playlist/{playlistId}") {
        val arguments = listOf(
            navArgument("playlistId") { type = NavType.StringType }
        )
        
        fun createRoute(playlistId: String) = "playlist/$playlistId"
    }
} 