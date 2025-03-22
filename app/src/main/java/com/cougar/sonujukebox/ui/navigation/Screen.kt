package com.cougar.sonujukebox.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.NamedNavArgument

sealed class Screen(
    val route: String,
    val title: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    object Artists : Screen(
        route = "artists",
        title = "Artists"
    )

    object ArtistDetail : Screen(
        route = "artist/{artistId}",
        title = "Artist Details",
        arguments = listOf(
            navArgument("artistId") { type = NavType.StringType }
        )
    )

    object Playlists : Screen(
        route = "playlists",
        title = "Playlists"
    )

    object PlaylistDetail : Screen(
        route = "playlist/{playlistId}",
        title = "Playlist Details",
        arguments = listOf(
            navArgument("playlistId") { type = NavType.StringType }
        )
    )

    companion object {
        fun createRoute(route: String, vararg args: Pair<String, String>): String {
            return buildString {
                append(route)
                if (args.isNotEmpty()) {
                    append("?")
                    args.forEachIndexed { index, (key, value) ->
                        if (index > 0) append("&")
                        append("$key=$value")
                    }
                }
            }
        }
    }
} 