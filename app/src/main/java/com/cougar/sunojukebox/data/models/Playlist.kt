package com.cougar.sunojukebox.data.models

data class Playlist(
    val id: String,
    val name: String,
    val tracks: List<Track>
) 