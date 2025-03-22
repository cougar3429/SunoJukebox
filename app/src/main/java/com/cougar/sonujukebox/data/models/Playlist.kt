package com.cougar.sonujukebox.data.models

data class Playlist(
    val id: String,
    val name: String,
    val tracks: List<Track>
) 